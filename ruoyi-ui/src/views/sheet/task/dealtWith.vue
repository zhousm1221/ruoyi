<template>
  <div class="app-container">
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
        label="任务名称"
        align="center"
        prop="taskName"
        :show-overflow-tooltip="true"
      >
        <template slot-scope="scope">
          <div class="blue-font-color" @click="handleModel(scope.row)">
            {{ scope.row.taskName }}
          </div>
        </template>
      </el-table-column>
      <el-table-column
        label="执行人"
        align="center"
        prop="executor"
        width="100"
      />
      <el-table-column
        label="发起人"
        align="center"
        prop="originator"
        width="100"
      />
      <el-table-column label="任务状态" align="center" prop="status" width="100">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.sys_notice_status"
            :value="scope.row.status"
          />
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
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
          >修改</el-button>
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
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
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
      </el-form>
      <div slot="footer" class="dialog-footer" style="width: 580px">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  dealtWithList,
  toDODetails,
  getNotice,
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
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        executor: 100,
        status: 0
      },
      issuedParams: {
        pageNum: 1,
        pageSize: 10,
        deptId: 200
      },
      // 表单参数
      form: {},

      // 表单校验
      rules: {
        noticeTitle: [
          { required: true, message: '公告标题不能为空', trigger: 'blur' }
        ],
        noticeType: [
          { required: true, message: '公告类型不能为空', trigger: 'change' }
        ]
      },
      templateList: '', // 模版列表
      issuedByList: '' // 人员下发列表
    }
  },
  created() {
    this.getInstList()
    this.getTemplate()
    this.getIssued()
  },
  methods: {
    /** 查询公告列表 */
    getInstList() {
      this.loading = true
      dealtWithList(this.queryParams).then((response) => {
        this.noticeList = response.rows
        this.total = response.total
        this.loading = false
      })
    },

    // 查看任务详情
    handleModel(row) {
      // const paramsTask = row.id + 'type =1'
      toDODetails(row.id).then((response) => {
        console.log(response, '333')
      })
    },
    // 报表模版查询
    getTemplate() {
      aboutList(this.queryParams).then((response) => {
        this.templateList = response.rows
      })
    },
    // 查询下发人员列表
    getIssued() {
      issuedList(this.issuedParams).then((response) => {
        this.issuedByList = response.rows
        console.log(response, 'response')
      })
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
        status: '0'
      }
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
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const noticeId = row.noticeId || this.ids
      getNotice(noticeId).then((response) => {
        this.form = response.data
        this.open = true
        this.title = '修改公告'
      })
    },
    /** 提交按钮 */
    submitForm: function() {
      const confirmParams = {
        topic: 'topic',
        modelId: this.form.template,
        createUser: this.form.issuedBy,
        executor: '200,201,202',
        type: 'type',
        status: '0'
      }
      confirmToIssue(confirmParams).then((response) => {
        if (response.msg === '操作成功') {
          this.$message({
            message: '新增成功',
            type: 'success'
          })
        }
        console.log(response, '9999')
      })
      // this.$refs['form'].validate((valid) => {
      //   if (valid) {
      //     if (this.form.noticeId != undefined) {
      //       updateNotice(this.form).then((response) => {
      //         this.$modal.msgSuccess('修改成功')
      //         this.open = false
      //         this.getList()
      //       })
      //     } else {
      //       addNotice(this.form).then((response) => {
      //         this.$modal.msgSuccess('新增成功')
      //         this.open = false
      //         this.getList()
      //       })
      //     }
      //   }
      // })
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
