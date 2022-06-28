<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['system:notice:add']"
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新增</el-button>
      </el-col>
      <right-toolbar :show-search.sync="showSearch" @queryTable="getInstList" />
    </el-row>

    <el-table
      v-loading="loading"
      :data="noticeList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column
        label="序号"
        align="center"
        type="index"
        width="100"
      />
      <el-table-column
        label="作业id"
        align="center"
        prop="instId"
        :show-overflow-tooltip="true"
      >
        <template slot-scope="scope">
          <div class="blue-font-color" @click="handleModel(scope.row)">
            {{ scope.row.instId }}
          </div>
        </template>
      </el-table-column>
      <el-table-column
        label="主题"
        align="center"
        prop="topic"
        width="100"
      />
      <el-table-column
        label="创建人"
        align="center"
        prop="createUser"
        width="100"
      />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            v-hasPermi="['system:notice:edit']"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >预览</el-button>
          <el-button
            v-hasPermi="['system:notice:remove']"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getInstList"
    />

    <!-- 添加或修改公告对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="780px" append-to-body>
      <el-form ref="form" :model="form" label-width="80px">
        <el-row>
          <div class="form">
            <div style="width: 100px; color: #606266;font-size: 14px;font-weight: bold;text-align:right; display: flex;justify-content: space-around"> 主                                           题</div>
            <el-input v-model="form.topic" placeholder="" />
          </div>
          <el-col :span="18">
            <el-form-item label="报表模版" prop="noticeType">
              <el-select v-model="form.template" placeholder="请选择报表模版">
                <el-option
                  v-for="item in templateList"
                  :key="item.modelId"
                  :label="item.modelName"
                  :value="item.modelId"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="18">
            <el-form-item label="下发人员" prop="noticeType">
              <!-- <el-checkbox v-model="checkAll" :indeterminate="isIndeterminate" @change="handleCheckAllChange">全选</el-checkbox> -->
              <div style="margin: 15px 0;" />
              <el-checkbox-group v-model="checkedCities" @change="handleCheckedCitiesChange">
                <el-checkbox v-for="item in issuedByList" :key="item.value" :label="item.value">{{ item.label }}</el-checkbox>
              </el-checkbox-group>
              <!-- <el-select v-model="form.issuedBy" placeholder="请选择下发人员">
                <el-option
                  v-for="item in issuedByList"
                  :key="item.userId"
                  :label="item.nickName"
                  :value="item.userId"
                />
              </el-select> -->
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer" style="width: 580px">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 作业ID弹框 -->
    <el-dialog :title="title" :visible.sync="openID" width="780px" append-to-body>
      <div class="open">
        <div>{{ taskInfo.topic }}</div>
        <div>{{ taskInfo.createUser }}</div>
        <div>{{ parseTime(taskInfo.createTime, '{y}-{m}-{d}') }}</div>
      </div>
      <el-table
        v-loading="loading"
        :data="taskList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column
          label="序号"
          align="center"
          type="index"
          width="200"
        />
        <el-table-column
          label="填报人"
          align="center"
          prop="executor"
          width="250"
        />
        <el-table-column
          label="填报情况"
          align="center"
          prop="status"
          width="250"
        />
        <!-- <el-form ref="form" :model="taskData" label-width="80px">
        <el-row>
          <el-col :span="18">
            <el-form-item label="报表模版" prop="noticeType">
              <el-select v-model="form.template" placeholder="请选择报表模版">
                <el-option
                  v-for="item in templateList"
                  :key="item.id"
                  :label="item.modelName"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="18">
            <el-form-item label="下发人员" prop="noticeType">
              <el-select v-model="form.issuedBy" placeholder="请选择下发人员">
                <el-option
                  v-for="item in issuedByList"
                  :key="item.userId"
                  :label="item.nickName"
                  :value="item.userId"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form> -->
      </el-table></el-dialog>
  </div>
</template>

<script>
import {
  getNotice,
  instJob,
  instTask,
  delNotice,
  aboutList,
  issuedList,
  instList,
  confirmToIssue
} from '@/api/system/notice'

export default {
  name: 'Notice',
  dicts: ['sys_notice_status', 'sys_notice_type'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 公告表格数据
      noticeList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      openID: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
      issuedParams: {
        pageNum: 1,
        pageSize: 10,
        deptId: 200
      },
      // 表单参数
      form: {},
      taskData: {},
      taskList: [],
      taskInfo: {},
      // 表单校验
      rules: {
        noticeTitle: [
          { required: true, message: '公告标题不能为空', trigger: 'blur' }
        ],
        noticeType: [
          { required: true, message: '公告类型不能为空', trigger: 'change' }
        ]
      },
      isIndeterminate: true,
      checkAll: false,
      templateList: '', // 模版列表
      checkedCities: [], //
      checkList: '',
      issuedByList: '' // 人员下发列表
    }
  },
  created() {
    this.getInstList()
    this.getTemplate()
    this.getIssued()
  },
  methods: {
    /** 下发作业列表 */
    getInstList() {
      this.loading = true
      const params = {
        pageNum: 1,
        pageSize: 10,
        createUser: localStorage.getItem('userName')
      }
      instList(params).then((response) => {
        this.noticeList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 报表模版查询
    getTemplate() {
      aboutList(this.queryParams).then((response) => {
        this.templateList = response.rows
        console.log(this.templateLis, '11222')
      })
    },
    // 查询下发人员列表
    getIssued() {
      issuedList(this.issuedParams).then((response) => {
        this.issuedByList = response.rows.map(item => {
          return {
            value: item.userId,
            label: item.nickName
          }
        })
        // this.issuedByList = response.rows
        // console.log(this.issuedByList, 'this.issuedByList')
        // const ab = this.issuedByList
        // ab.map(item => {
        //   return {
        //     value: item.userId,
        //     label: item.nickName
        //   }
        // })
        console.log(this.issuedByList, '1111')
      })
    },

    // 点击作业ID弹框
    handleModel(row) {
      instJob(row.instId).then((response) => {
        this.$router.push({ path: '/sheet/filling', query: { task: response.data }})
      })
    },
    // 全选
    // handleCheckAllChange(val) {
    //   console.log(val, '1111')

    //   if (val === true) {
    //     this.checkedCities.push(this.issuedByList.map(item => {
    //       return item.label
    //     }))
    //     console.log(this.checkedCities, '111')
    //   }
    //   // this.checkedCities = val ? this.issuedByList : []
    //   this.isIndeterminate = false
    // },
    //
    handleCheckedCitiesChange(value) {
      this.checkList = value
      const checkedCount = value.length
      this.checkAll = checkedCount === this.issuedByList.length
      this.isIndeterminate = checkedCount > 0 && checkedCount < this.issuedByList.length
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        noticeId: undefined,
        noticeTitle: undefined,
        noticeType: undefined,
        noticeContent: undefined,
        topic: undefined,
        template: undefined,
        status: '0'
      }
      this.checkList = ''
      this.checkedCities = []
      this.resetForm('form')
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.noticeId)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '作业下发'
    },
    /** 预览按钮操作 */
    handleUpdate(row) {
      instTask(row.instId).then((response) => {
        this.taskInfo = response.data
        this.taskList = response.data.taskInfo
        console.log(response, '2345')
      })
      this.openID = true
      this.reset()
    },
    /** 提交按钮 */
    submitForm: function() {
      const confirmParams = {
        topic: this.form.topic,
        modelId: this.form.template,
        createUser: localStorage.getItem('userName'),
        executor: this.checkList.toString(),
        type: 'type',
        status: '0'
      }
      confirmToIssue(confirmParams).then((response) => {
        if (response.msg === '操作成功') {
          this.$message({
            message: '新增成功',
            type: 'success'
          })
          this.open = false
        }
        console.log(response, '9999')
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const noticeIds = row.noticeId || this.ids
      this.$modal
        .confirm('是否确认删除公告编号为"' + noticeIds + '"的数据项？')
        .then(function() {
          return delNotice(noticeIds)
        })
        .then(() => {
          this.getInstList()
          this.$modal.msgSuccess('删除成功')
        })
        .catch(() => {})
    }
  }
}
</script>
<style scoped>
.form {
  display: flex;
  margin-bottom: 20px;
  color: #606266;
}
.open {
  display: flex;
  justify-content: space-between;
}
/deep/.el-input--medium .el-input__inner {
  width: 200px;
}
</style>
