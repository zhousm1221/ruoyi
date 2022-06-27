<template>
  <div class="hello">
    <div style="" class="box-top">
      <input style="font-size: 16px" type="file" @change="uploadExcel">
      <el-button
        type="small"
        class="box-down"
        @click="downloadExcel"
      >导出</el-button>

      <el-button class="saveSheet" size="small" @click="save()">保存</el-button>
    </div>

    <div
      id="luckysheet"
      style="
        margin: 0px;
        padding: 0px;
        position: absolute;
        width: 100%;
        height: 550px;
        left: 0px;
        top: 30px;
        bottom: 0px;
      "
    />

    <div
      v-show="isMaskShow"
      style="
        position: absolute;
        z-index: 1000000;
        left: 0px;
        top: 0px;
        bottom: 0px;
        right: 0px;
        background: rgba(255, 255, 255, 0.8);
        text-align: center;
        font-size: 40px;
        align-items: center;
        justify-content: center;
        display: flex;
      "
    >
      Downloading
    </div>
  </div>
</template>

<script>
import LuckyExcel from 'luckyexcel'
// 导入库export.js 这个文件是es6的，不能在普通的HTML文件直接引入js文件（虽然都是js文件，但是有区别，具体请百度es6与es5）！需要把es6转es5才可以直接引入使用！
import { exportExcel } from '@/utils/export'
import { addExcelData } from '@/api/system/notice'

export default {
  name: 'HelloWorld',
  props: {
    msg: String
  },
  data() {
    return {
      selected: '',
      isMaskShow: false,
      celldata: '',
      excelList: ''
    }
  },
  created() {
    if (this.$route.query.add) {
      this.getExcelData()
      this.init()
    } else if (this.$route.query.addData) {
      this.excelList = this.$route.query.addData.content.celldata
      console.log(this.$route.query.addData, '0000')
    }
  },
  mounted() {
    // In some cases, you need to use $nextTick
    this.init()
  },
  methods: {
    // 初始化图标
    init() {
      const options = {
        container: 'luckysheet',
        lang: 'zh',
        gridKey: 'excel001',
        data: [
          {
            // name: "Cell", //工作表名称
            // color: "", //工作表颜色
            index: 0, // 工作表索引
            status: 1, // 激活状态
            order: 0, // 工作表的下标
            hide: 0, // 是否隐藏
            row: 36, // 行数
            column: 18, // 列数
            defaultRowHeight: 19, // 自定义行高
            defaultColWidth: 73, // 自定义列宽
            celldata: [], // 初始化使用的单元格数据
            config: {
              merge: {}, // 合并单元格
              rowlen: {}, // 表格行高
              columnlen: {}, // 表格列宽
              rowhidden: {}, // 隐藏行
              colhidden: {}, // 隐藏列
              borderInfo: {}, // 边框
              authority: {} // 工作表保护
            },
            scrollLeft: 0, // 左右滚动条位置
            scrollTop: 0, // 上下滚动条位置
            luckysheet_select_save: [], // 选中的区域
            calcChain: [], // 公式链
            isPivotTable: false, // 是否数据透视表
            pivotTable: {}, // 数据透视表设置
            filter_select: {}, // 筛选范围
            filter: null, // 筛选配置
            luckysheet_alternateformat_save: [], // 交替颜色
            luckysheet_alternateformat_save_modelCustom: [], // 自定义交替颜色
            luckysheet_conditionformat_save: {}, // 条件格式
            frozen: {}, // 冻结行列配置
            chart: [], // 图表配置
            zoomRatio: 1, // 缩放比例
            image: [], // 图片
            showGridLines: 1, // 是否显示网格线
            dataVerification: {}, // 数据验证配置
            celldata: this.excelList
          }
        ],
        hook: {
          workbookCreateAfter: function() {
            // luckysheet.hideRow(1, 3);
            // luckysheet.hideRow(8, 10);
          },
          cellRightClickConfig: {
            // hideRow: false, // 隐藏选中行和显示选中行
            // hideColumn: false, // 隐藏选中列和显示选中列
          }
        }
      }
      luckysheet.create(options)
    },
    // 获取页面数据
    getExcelData() {},
    uploadExcel(evt) {
      const files = evt.target.files
      if (files == null || files.length == 0) {
        alert('No files wait for import')
        return
      }

      const name = files[0].name
      const suffixArr = name.split('.')
      const suffix = suffixArr[suffixArr.length - 1]
      if (suffix != 'xlsx') {
        alert('Currently only supports the import of xlsx files')
        return
      }
      LuckyExcel.transformExcelToLucky(
        files[0],
        function(exportJson, luckysheetfile) {
          if (exportJson.sheets == null || exportJson.sheets.length == 0) {
            alert(
              'Failed to read the content of the excel file, currently does not support xls files!'
            )
            return
          }
          window.luckysheet.destroy()

          window.luckysheet.create({
            container: 'luckysheet', // luckysheet is the container id
            showinfobar: false,
            data: exportJson.sheets,
            title: exportJson.info.name,
            userInfo: exportJson.info.name.creator
          })
        }
      )
    },
    selectExcel(evt) {
      const value = this.selected
      const name = evt.target.options[evt.target.selectedIndex].innerText

      if (value == '') {
        return
      }
      this.isMaskShow = true

      LuckyExcel.transformExcelToLuckyByUrl(
        value,
        name,
        (exportJson, luckysheetfile) => {
          if (exportJson.sheets == null || exportJson.sheets.length == 0) {
            alert(
              'Failed to read the content of the excel file, currently does not support xls files!'
            )
            return
          }

          this.isMaskShow = false
          window.luckysheet.destroy()

          window.luckysheet.create({
            container: 'luckysheet', // luckysheet is the container id
            showinfobar: false,
            data: exportJson.sheets,
            title: exportJson.info.name,
            userInfo: exportJson.info.name.creator
          })
        }
      )
    },
    downloadExcel() {
      // const value = this.selected;;
      //
      // if(value.length==0){
      //     alert("Please select a demo file");
      //     return;
      // }
      //
      // var elemIF = document.getElementById("Lucky-download-frame");
      // if(elemIF==null){
      //     elemIF = document.createElement("iframe");
      //     elemIF.style.display = "none";
      //     elemIF.id = "Lucky-download-frame";
      //     document.body.appendChild(elemIF);
      // }
      // elemIF.src = value;
      exportExcel(luckysheet.getAllSheets(), '下载')
      // testaaa();
    },
    async save() {
      this.celldata = window.luckysheet.transToCellData(
        window.luckysheet.getLuckysheetfile(2)[0].data
      )
      const params = {
        modelName: '测试模板001',
        content: {
          name: 'Cell',
          index: 'sheet_01',
          order: 0,
          status: 1,
          celldata: this.celldata
        },
        createUser: 100,
        status: 1
      }
      addExcelData(params).then((response) => {
        if (response.msg === '操作成功') {
          this.$message({
            message: '保存成功',
            type: 'success'
          })
          setTimeout(() => {
            this.$router.push({ path: '/sheet/template' })
          }, 2000)
        }
        console.log(response, '000')
      })
      // this.dataUp()
      console.log(this.celldata, 'this.celldata')
    }
    // dataUp() {
    //   window.luckysheet.setDataVerification(
    //     {
    //       type: 'number_integer',
    //       type2: 'gte',
    //       value1: '1',
    //       value2: '',
    //       checked: false,
    //       remote: false,
    //       prohibitInput: true,
    //       hintShow: true,
    //       hintText: '请输入整数哦'
    //     },
    //     {
    //       range: { row: [1, 100], column: [0, 1] }
    //     }
    //   )
    // }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
.box-top {
  position: absolute;
  top: 10px;
}
.box-down {
}
.saveSheet {
  right: 0;
}
.saveSheet {
  margin-left: 80px;
}
/deep/ .luckysheet {
  margin-top: 20px;
}
</style>
