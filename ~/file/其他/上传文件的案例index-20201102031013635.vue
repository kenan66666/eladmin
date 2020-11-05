<template>
  <div class="dashboard-container">
    <div class="dashboard-editor-container">
      <!--表单组件-->
      <el-dialog
        append-to-body
        :close-on-click-modal="false"
        :before-close="cancel"
        :visible.sync="isUpload"
        title="导入部门级衡量指标信息"
        width="500px"
      >
        <!-- <el-form ref="form" :model="form" size="small" label-width="80px"> -->
        <el-form
          ref="uploadForm"
          size="small"
          label-width="100px"
          :model="uploadForm"
          :rules="uploadRules"
        >
          <!-- v-model="form.planYear" -->
          <el-form-item label="BSC年份" prop="uploadYearMonth">
            <el-date-picker
              clearable
              v-model="uploadForm.uploadYearMonth"
              type="year"
              placeholder="BSC年份"
              style="width: 100%"
              class="filter-item-2"
            />
          </el-form-item>
          <!--   上传文件   -->
          <el-form-item label="上传" prop="fileList">
            <el-upload
              ref="upload"
              :on-change="onChange"
              :file-list="uploadForm.fileList"
              :limit="2"
              :before-upload="beforeUpload"
              :auto-upload="false"
              :on-success="handleSuccess"
              :on-error="handleError"
              :action="uploadUrl"
              :http-request="uploadAction"
            >
              <div class="eladmin-upload">
                <i class="el-icon-upload" /> 添加文件
              </div>
              <div slot="tip" class="el-upload__tip">只可上传xlsx格式文件，且不超过100M</div>
            </el-upload>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="cancel">取消</el-button>
          <el-button :loading="loading" type="primary" @click="upload">确认</el-button>
        </div>
      </el-dialog>

      <el-dialog
        class="myform"
        append-to-body
        :close-on-click-modal="false"
        :before-close="editCancel"
        :visible="isShow"
        :title="title"
        width="500px"
      >
        <el-form ref="form" size="small" label-width="150px" :model="form" :rules="rules">
          <el-form-item label="BSC年份" prop="year_month">
            <el-date-picker
              style="width: 320px;"
              @change="dateChange"
              v-model="year_month"
              type="year"
              placeholder="BSC年份"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="维度" prop="dimension">
            <!-- <el-input v-model="form.dimension" style="width: 320px;" /> -->
            <el-select
              v-model="form.dimension"
              class="filter-item"
              style="width: 320px"
              placeholder="维度"
              clearable
              @change="dimensionChange"
            >
              <el-option label="DF-成本领先" value="成本领先" />
              <el-option label="DC-与众不同" value="与众不同" />
              <el-option label="DI-创新创造" value="创新创造" />
              <el-option label="DL-学习成长" value="学习成长" />
            </el-select>
          </el-form-item>
          <el-form-item label="主题" prop="theme">
            <!-- <el-input v-model="form.theme" style="width: 320px;" /> -->
            <el-select
              v-model="form.theme"
              style="width: 320px;"
              filterable
              allow-create
              default-first-option
              placeholder="创建或选择主题"
            >
              <el-option
                v-for="item in themeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="战略目标序号" prop="strategy_target_no">
            <!-- <el-input v-model="form.strategy_target_no" style="width: 320px;" /> -->
            <el-input
              @change="targetNoChange"
              oninput="value=value.replace(/[^\d]/g,'')"
              type="number"
              :min="1"
              v-model="form.strategy_target_no"
              style="width: 320px;"
            >
              <template slot="prepend">{{target_no_prefix}}</template>
            </el-input>
          </el-form-item>
          <el-form-item label="支撑的战略目标名称" prop="strategy_target_name">
            <el-input v-model="form.strategy_target_name" style="width: 320px;" />
          </el-form-item>
          <el-form-item label="指标序号" prop="index_no">
            <!-- <el-input v-model="form.index_no" style="width: 320px;" /> -->
            <el-input
              type="number"
              oninput="value=value.replace(/[^\d]/g,'')"
              :min="1"
              v-model="form.index_no"
              style="width: 320px;"
            >
              <template slot="prepend">{{index_no_prefix}}</template>
            </el-input>
          </el-form-item>
          <el-form-item label="指标名称" prop="index_name">
            <el-input v-model="form.index_name" style="width: 320px;" />
          </el-form-item>
          <el-form-item label="指标类型" prop="index_type">
            <!-- <el-input v-model="form.index_type" style="width: 320px;" /> -->
            <el-select
              v-model="form.index_type"
              class="filter-item"
              style="width: 320px"
              placeholder="指标类型"
              clearable
            >
              <el-option label="量化指标" value="量化指标" />
              <el-option label="项目指标" value="项目指标" />
            </el-select>
          </el-form-item>
          <el-form-item label="责任部门" prop="duty_dep">
            <!-- <el-input v-model="form.duty_dep" style="width: 320px;" /> -->
            <el-select
              v-model="form.duty_dep"
              class="filter-item"
              style="width: 320px"
              placeholder="责任部门"
              clearable
            >
              <el-option
                v-for="item in depOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="数据填报部门" prop="report_dep">
            <el-select
              v-model="form.report_dep"
              class="filter-item"
              style="width: 320px"
              placeholder="填报部门"
              clearable
            >
              <el-option
                v-for="item in depOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="更新频率" prop="update_freq">
            <!-- <el-input v-model="form.update_freq" style="width: 320px;" /> -->
            <el-select
              v-model="form.update_freq"
              style="width: 320px;"
              filterable
              allow-create
              default-first-option
              placeholder="创建或选择更新频率"
            >
              <el-option label="月度" value="月度" />
              <el-option label="季度" value="季度" />
              <el-option label="年度" value="年度" />
            </el-select>
          </el-form-item>
          <el-form-item label="单位" prop="unit">
            <el-input v-model="form.unit" style="width: 320px;" />
          </el-form-item>
          <el-form-item label="状态公式" prop="formula_id">
            <el-select
              v-model="form.formula_id"
              class="filter-item"
              style="width: 320px"
              placeholder="状态公式"
              clearable
            >
              <el-option
                v-for="item in formulaOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click.native="editCancel">取消</el-button>
          <el-button :loading="loading" type="primary" @click="submit">确认</el-button>
        </div>
      </el-dialog>
      <el-card class="box-card">
        <div>
          <!-- 查询框 -->
          <!-- <div style="padding-bottom:5px">{{ title }}</div> -->
          <div class="flex-div" style="padding-bottom:5px">
            <div class="block">
              <el-date-picker
                class="filter-item"
                style="width: 120px;"
                v-model="queryForm.year"
                type="year"
                :editable="false"
                :clearable="false"
                value-format="yyyy"
                placeholder="BSC年份"
              ></el-date-picker>
              <el-select
                v-model="queryForm.dimension"
                class="filter-item"
                style="width: 120px"
                placeholder="维度"
                clearable
              >
                <el-option
                  v-for="item in dimensionOption"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
              <el-select
                v-model="queryForm.theme"
                class="filter-item"
                style="width: 120px"
                placeholder="主题"
                clearable
              >
                <el-option
                  v-for="item in themeOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
              <el-select
                v-model="queryForm.duty_dep"
                class="filter-item"
                style="width: 120px"
                placeholder="责任部门"
                clearable
              >
                <el-option
                  v-for="item in depOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
              <el-select
                v-model="queryForm.report_dep"
                class="filter-item"
                style="width: 120px"
                placeholder="填报部门"
                clearable
              >
                <el-option
                  v-for="item in depOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
              <el-select
                v-model="queryForm.is_release"
                class="filter-item"
                style="width: 120px"
                placeholder="发布状态"
                clearable
              >
                <el-option label="已发布" :value="1" />
                <el-option label="未发布" :value="0" />
              </el-select>
              <!-- <el-input v-model="queryForm.version" style="width: 120px;" clearable placeholder="版本号" /> -->
              <el-input
                v-model="queryForm.index_name"
                style="width: 120px;"
                clearable
                placeholder="指标名称"
              />
              <el-input
                v-model="queryForm.strategy_target_name"
                style="width: 120px;"
                clearable
                placeholder="战略目标名称"
              />
              <el-button
                class="filter-item"
                size="mini"
                type="success"
                icon="el-icon-search"
                @click="search"
              >查询</el-button>
              <el-button
                class="filter-item"
                size="mini"
                type="warning"
                icon="el-icon-refresh-left"
                @click="reset"
              >重置</el-button>
            </div>
            <!-- <div>
              <el-button
                class="filter-item"
                type="primary"
                size="mini"
                plain
                style="width: 100px;"
                icon="el-icon-download"
                @click="downloading"
              >导出</el-button>
            </div>-->
          </div>
          <!-- 操作区域 -->
          <div style="margin-bottom:10px;">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="add">新增</el-button>
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="del()">删除</el-button>
            <el-button
              class="filter-item"
              size="mini"
              type="warning"
              icon="el-icon-upload"
              @click="showUpload"
            >导入</el-button>
            <el-button
              class="filter-item"
              type="primary"
              size="mini"
              plain
              icon="el-icon-download"
              @click="downloading"
            >导出</el-button>
            <el-button
              :loading="releaseLoading"
              type="primary"
              icon="el-icon-check"
              size="mini"
              @click="release()"
            >发布</el-button>
            <el-button
              class="filter-item"
              type="success"
              size="mini"
              plain
              icon="el-icon-download"
              @click="templateDownload"
            >模板下载</el-button>
          </div>
          <!-- 数据区域 -->
          <el-table
            id="table"
            v-loading="tableLoading"
            target="#table"
            :data="tableData"
            element-loading-text="拼命加载中"
            element-loading-background="rgba(0, 0, 0, 0.6)"
            header-row-class-name="table-header"
            style="width: 100%;margin-bottom: 20px;"
            row-key="id"
            border
            :height="height"
            default-expand-all
            :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
            @selection-change="handleSelectionChange"
          >
            <el-table-column
              type="selection"
              width="55"
              :selectable="(row)=>{
                return row.is_release !=1
              }"
            />
            <el-table-column
              v-for="(item,index) in tableHeader"
              :key="index"
              :fixed="item.fixed"
              :prop="item.prop"
              :label="item.label"
              :width="item.width"
            />
            <el-table-column align="center" prop="released_time" label="最新发布时间">
              <template slot-scope="scope">
                <span>{{ parseTime(scope.row.released_time) }}</span>
              </template>
            </el-table-column>
            <el-table-column align="center" prop="is_delete" label="发布状态">
              <template slot-scope="scope">
                <span>{{ scope.row.is_release===1?'已发布':'未发布' }}</span>
              </template>
            </el-table-column>
            <el-table-column fixed="right" label="操作" width="120">
              <template slot-scope="scope">
                <el-button
                  :disabled="scope.row.is_release ===1"
                  type="primary"
                  icon="el-icon-edit"
                  size="mini"
                  @click="edit(scope.row)"
                />
                <el-button
                  v-if="checkPermission(['actionplan_detail:modi'])"
                  :disabled="scope.row.index_no.indexOf('D') === -1"
                  type="danger"
                  icon="el-icon-delete"
                  size="mini"
                  @click="delIgnore(scope.row.id)"
                />
                <el-button
                  v-else
                  :disabled="scope.row.index_no.indexOf('D') === -1"
                  type="danger"
                  icon="el-icon-delete"
                  size="mini"
                  @click="del(scope.row.id)"
                />
              </template>
            </el-table-column>
          </el-table>
          <!-- </el-card> -->
          <!-- <el-pagination
            :current-page.sync="currentPage"
            :page-size="pagesize"
            layout="total,sizes, prev, pager, next, jumper"
            small
            class="shoppingPagination"
            :page-sizes="[12, 20, 30]"
            :total="totalElements"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
          />-->
          <div style="text-align:center" />
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
import crudMeasure from "@/api/bsc/deptMeasure";
import crudBscDep from "@/api/bsc/bscDep";
import crudExpression from "@/api/bsc/expression";
import { parseTime } from "@/utils/index";
import { mapGetters } from "vuex";
import Cookies from "js-cookie";
import { upload2 } from "@/utils/upload";
import checkPermission from "@/utils/permission";
import { download } from '@/api/data'
import { downloadFile } from '@/utils/index'
export default {
  name: "BaseDeptMeasure",
  components: {},
  data() {
    return {
      uploadForm: {
        uploadYearMonth: new Date(),
        fileList: [],
      },
      year_month: new Date(),
      index_no_prefix: "", //指标序号前缀
      target_no_prefix: "", //战略目标序号前缀
      id: "",
      changeType: "", // upload控件change事件类型：success-成功，error-失败
      file: "",
      releaseLoading: false, //发布按钮loading状态
      tableLoading: false,
      currentPage: 1,
      currpage: 0,
      pagesize: 12,
      totalElements: 0,
      selectDataOne: {},
      uploadUrl: "",
      selectData: [],
      isUpload: false,
      isShow: false,
      loading: false,
      queryForm: {
        year: new Date().getFullYear() + "",
        dimension: "",
        index_name: "",
        strategy_target_name: "",
        theme: "",
        stylduty_dep: "",
        sreport_dep: "",
        is_release: "",
      },
      form: {
        year_month: new Date(),
        year: "",
        month: "",
        dimension: "",
        theme: "",
        index_no: "",
        index_name: "",
        index_type: "",
        strategy_target_name: "",
        strategy_target_no: "",
        duty_dep: "",
        report_dep: "",
        update_freq: "",
        formula_id: "",
        unit: "",
      },
      rules: {
        year_month: [
          { required: true, message: "BSC年月不能为空。", trigger: "blur" },
        ],
        dimension: [
          { required: true, message: "维度不能为空。", trigger: "blur" },
        ],
        theme: [{ required: true, message: "主题不能为空。", trigger: "blur" }],
        index_no: [
          { required: true, message: "指标序号不能为空。", trigger: "blur" },
        ],
        index_name: [
          { required: true, message: "指标名称不能为空。", trigger: "blur" },
        ],
        index_type: [
          { required: true, message: "指标类型不能为空。", trigger: "blur" },
        ],
        strategy_target_name: [
          {
            required: true,
            message: "战略指标名称不能为空。",
            trigger: "blur",
          },
        ],
        strategy_target_no: [
          {
            required: true,
            message: "战略指标序号不能为空。",
            trigger: "blur",
          },
        ],
        duty_dep: [
          { required: true, message: "责任部门不能为空。", trigger: "blur" },
        ],
        report_dep: [
          { required: true, message: "填报部门不能为空。", trigger: "blur" },
        ],
        update_freq: [
          { required: true, message: "更新频率不能为空。", trigger: "blur" },
        ],
        formula_id: [
          { required: true, message: "状态公式不能为空。", trigger: "blur" },
        ],
      },
      uploadRules: {
        uploadYearMonth: [
          { required: true, message: "BSC年月不能为空。", trigger: "blur" },
        ],
        fileList: [
          { required: true, message: "上传文件不能为空。", trigger: "blur" },
        ],
      },
      title: "公司级衡量指标信息维护",
      tableHeader: [
        {
          prop: "year",
          label: "维护年份",
          width: 60,
          fixed: true,
          children: [],
        },
        {
          prop: "dimension",
          label: "维度",
          fixed: true,
          width: 80,
          children: [],
        },
        {
          prop: "theme",
          label: "主题",
          fixed: false,
          // width:60,
          children: [],
        },
        {
          prop: "strategy_target_no",
          label: "战略目标序号",
          fixed: false,
          // width:60,
          children: [],
        },
        {
          prop: "strategy_target_name",
          label: "战略目标名称",
          fixed: false,
          // width:60,
          children: [],
        },
        {
          prop: "index_no",
          label: "指标序号",
          fixed: false,
          // width:60,
          children: [],
        },
        {
          prop: "index_name",
          label: "指标名称",
          fixed: false,
          // width:60,
          children: [],
        },
        {
          prop: "update_freq",
          label: "更新频率",
          fixed: false,
          // width:60,
          children: [],
        },
        {
          prop: "unit",
          label: "单位",
          fixed: false,
          // width:60,
          children: [],
        },
        {
          prop: "index_type",
          label: "指标类型",
          fixed: false,
          // width:60,
          children: [],
        },
        {
          prop: "duty_dep",
          label: "责任部门",
          fixed: false,
          // width:60,
          children: [],
        },
        {
          prop: "report_dep",
          label: "填报部门",
          fixed: false,
          // width:60,
          children: [],
        },
        {
          prop: "formula_name",
          label: "状态公式",
          fixed: false,
          // width:60,
          children: [],
        },
      ],
      tableData: [],
      depOptions: [], //部门
      formulaOptions: [], //状态公式下拉框
      themeOptions: [], //主题
      dimensionOption: [], //维度
      bscDeptName: "", //bsc部门信息
    };
  },
  watch: {},

  created() {
    this.initialize(1);
    //获取部门信息
    //通过角色信息获取部门编号
    const roles = this.$store.getters && this.$store.getters.roles;
    let bscDeptNo = [""];
    for (let i in roles) {
      if (roles[i].indexOf("BSC-DEPT-") > -1) {
        bscDeptNo.push(roles[i].replace("BSC-DEPT-", ""));
      }
    }
    crudBscDep.queryBscDepByNo({ no: bscDeptNo.join(",") }).then((res) => {
      if (res.success) {
        this.depOptions = [];
        let data = res.data[0].data;
        let depts = [];
        for (let i in data) {
          this.depOptions.push({
            label: data[i].dep_name,
            value: data[i].dep_name,
          });
          depts.push(data[i].dep_name)
        }


        //初始化下拉框数据
        var params = {
          year:this.queryForm.year,
          reportDep:depts.join(',')
        }
        crudMeasure.drowdown(params).then((res) => {
          if (res.success) {
            //查询维度下拉框
            let dimensionArr = res.data[0].dimension;
            this.dimensionOption = [];
            for (let i in dimensionArr) {
              this.dimensionOption.push({
                value: dimensionArr[i].dimension,
                label: dimensionArr[i].dimension,
              });
            }
            //查询主题下拉框
            let themeArr = res.data[0].theme;
            this.themeOptions = [];
            for (let i in themeArr) {
              this.themeOptions.push({
                value: themeArr[i].theme,
                label: themeArr[i].theme,
              });
            }
          }
        });
      }
    });

    //获取公式信息，填充下拉框数据
    crudExpression.query().then((res) => {
      // console.log(res)
      if (res.content && res.content.length > 0) {
        this.formulaOptions = [];
        for (let i in res.content) {
          this.formulaOptions.push({
            value: res.content[i].id,
            label: res.content[i].formulaName,
          });
        }
      }
    });

    
  },
  computed: {
    ...mapGetters(["baseApi", "user"]),
    height() {
      return document.documentElement.clientHeight - 250; //减去顶部高度和底部高度
    },
  },
  methods: {
    checkPermission,
    parseTime,

    //导出excel
    downloading() {
      var params = {
        year: this.queryForm.year, //
        dimension: this.queryForm.dimension, //维度
        indexName: this.queryForm.index_name, //指标名称
        strategyTargetName: this.queryForm.strategy_target_name, //战略指标名称
        theme: this.queryForm.theme,
        dutyDep: this.queryForm.duty_dep,
        reportDep: this.queryForm.report_dep,
        isRelease: this.queryForm.is_release,
        isDelete: "0",
        sort: "id,desc",
      }
      this.$message.success('开始下载')
      download('api/bscMeasureIndexDep/download', params).then(res => {
        downloadFile(res, '衡量指标（部门）', 'xlsx')
      }).catch(err => {
        console.log(err)
      })
    },
    //模板下载
    templateDownload(){
      this.$message.success('开始下载')
      download('api/bscMeasureIndexDep/template', {}).then(res => {
        downloadFile(res, '衡量指标（部门）上传模板', 'xlsx')
      }).catch(err => {
        console.log(err)
      })
    },

    //新增界面日期选择框
    dateChange(value) {
      // console.log(value)
      this.$set(this.form, "year_month", value);
    },
    //维度改变时触发
    dimensionChange(value) {
      if ("成本领先" === value) {
        this.target_no_prefix = "DF";
      } else if ("与众不同" === value) {
        this.target_no_prefix = "DC";
      } else if ("创新创造" === value) {
        this.target_no_prefix = "DI";
      } else if ("学习成长" === value) {
        this.target_no_prefix = "DL";
      }
      this.index_no_prefix =
        this.target_no_prefix + this.index_no_prefix.substring(2);
    },
    //战略目标序号改变时触发
    targetNoChange(value) {
      this.index_no_prefix = this.target_no_prefix + value;
    },
    handleSizeChange(val) {
      this.pagesize = val;
      this.initialize(1);
    },
    add() {
      this.type = "post";
      this.title = "增加衡量指标信息";
      this.isShow = true;
      this.resetAdd();
    },
    //单条新增
    submit() {
      let validateResult = true;
      if (this.$refs["form"]) {
        this.$refs["form"].validate((valid) => {
          if (!valid) {
            validateResult = false;
            return;
          }
        });
      }
      if (!validateResult) {
        return;
      }
      this.loading = true
      if (this.form.id) {
        //编辑
        let params = {
          id: this.form.id,
          dimension: this.form.dimension,
          dutyDep: this.form.duty_dep,
          indexName: this.form.index_name,
          indexNo: this.index_no_prefix + "." + this.form.index_no,
          indexType: this.form.index_type,
          reportDep: this.form.report_dep,
          strategyTargetName: this.form.strategy_target_name,
          strategyTargetNo:
            this.target_no_prefix + this.form.strategy_target_no,
          theme: this.form.theme,
          unit: this.form.unit,
          updateFreq: this.form.update_freq,
          year: this.form.year_month.getFullYear(),
          // "bscMonth": this.form.year_month.getMonth() + 1,
          formulaId: this.form.formula_id,
        };
        crudMeasure
          .edit(params)
          .then((res) => {
            this.loading = false
            if (res.success) {
              this.$notify({
                title: "修改成功。",
                type: "success",
                duration: 2500,
              });
              this.initialize(1);
              this.editCancel();
            } else {
              this.$notify({
                title: res.message,
                type: "error",
                duration: 5000,
              });
            }
          })
          .catch(() => {
            this.loading = false
            this.$notify({
              title: "修改异常。",
              type: "error",
              duration: 5000,
            });
          });
      } else {
        let params = {
          dimension: this.form.dimension,
          dutyDep: this.form.duty_dep,
          indexName: this.form.index_name,
          indexNo: this.index_no_prefix + "." + this.form.index_no,
          indexType: this.form.index_type,
          reportDep: this.form.report_dep,
          strategyTargetName: this.form.strategy_target_name,
          strategyTargetNo:
            this.target_no_prefix + this.form.strategy_target_no,
          theme: this.form.theme,
          unit: this.form.unit,
          updateFreq: this.form.update_freq,
          year: this.form.year_month.getFullYear(),
          // "bscMonth": this.form.year_month.getMonth() + 1,
          formulaId: this.form.formula_id,
        };
        crudMeasure
          .add(params)
          .then((res) => {
            this.loading = false
            if (res.success) {
              this.$notify({
                title: "新增成功。",
                type: "success",
                duration: 2500,
              });
              this.initialize(1);
              this.editCancel();
            } else {
              this.$notify({
                title: res.message,
                type: "error",
                duration: 5000,
              });
            }
          })
          .catch(() => {
            this.loading = false
            this.$notify({
              title: "新增异常。",
              type: "error",
              duration: 5000,
            });
          });
      }
    },
    //发布
    release(id) {
      let ids = [];
      if (id) {
        ids.push(id);
      } else if (this.selectData.length > 0) {
        for (let i = 0; i < this.selectData.length; i++) {
          ids.push(this.selectData[i].id);
        }
      } else {
        this.$notify({
          title: "没有选择任何数据，请确认。",
          type: "error",
          duration: 2500,
        });
        return;
      }
      this.$confirm("发布之后将无法修改，确定发布选中的数据吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.releaseLoading = true;
        crudMeasure
          .release(ids)
          .then((res) => {
            this.releaseLoading = false;
            if (res.success) {
              this.$notify({
                title: "发布成功。",
                type: "success",
                duration: 2500,
              });
              this.initialize(1);
            } else {
              this.$notify({
                title: res.message,
                type: "error",
                duration: 5000,
              });
            }
          })
          .catch(() => {
            this.releaseLoading = false;
            this.$notify({
              title: "发布异常。",
              type: "error",
              duration: 5000,
            });
          });
      });
    },
    //编辑
    edit(data) {
      this.form = JSON.parse(JSON.stringify(data));
      //显示时只显示后面的数值
      this.index_no_prefix = this.form.index_no.substring(
        0,
        this.form.index_no.indexOf(".")
      );
      this.form.index_no = this.form.index_no.substring(
        this.form.index_no.indexOf(".") + 1
      );
      this.target_no_prefix = this.form.strategy_target_no.substring(0, 2);
      this.form.strategy_target_no = this.form.strategy_target_no.substring(2);
      //对年月进行处理，转换为date类型
      this.form.year_month = new Date(this.form.year + "-01");
      this.year_month = new Date(this.form.year + "-01");
      this.title = "修改衡量指标信息";
      this.isShow = true;
    },
    //强制删除
    delIgnore(id) {
      let ids = [];
      if (id) {
        ids.push(id);
      } else if (this.selectData.length > 0) {
        for (let i = 0; i < this.selectData.length; i++) {
          ids.push(this.selectData[i].id);
        }
      } else {
        this.$notify({
          title: "没有选择任何数据，请确认。",
          type: "error",
          duration: 2500,
        });
        return;
      }
      this.$confirm("确定删除选中的数据吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        crudMeasure
          .delIgnore(ids)
          .then((res) => {
            if (res.success) {
              this.$notify({
                title: "删除成功。",
                type: "success",
                duration: 2500,
              });
              this.initialize(1);
            } else {
              this.$notify({
                title: res.message,
                type: "error",
                duration: 2500,
              });
            }
          })
          .catch(() => {
            this.$notify({
              title: "删除异常",
              type: "error",
              duration: 2500,
            });
          });
      });
    },
    //删除
    del(id) {
      let ids = [];
      if (id) {
        ids.push(id);
      } else if (this.selectData.length > 0) {
        for (let i = 0; i < this.selectData.length; i++) {
          ids.push(this.selectData[i].id);
        }
      } else {
        this.$notify({
          title: "没有选择任何数据，请确认。",
          type: "error",
          duration: 2500,
        });
        return;
      }
      this.$confirm("确定删除选中的数据吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        crudMeasure
          .del(ids)
          .then((res) => {
            if (res.success) {
              this.$notify({
                title: "删除成功。",
                type: "success",
                duration: 2500,
              });
              this.initialize(1);
            } else {
              this.$notify({
                title: res.message,
                type: "error",
                duration: 2500,
              });
            }
          })
          .catch(() => {
            this.$notify({
              title: "删除异常",
              type: "error",
              duration: 2500,
            });
          });
      });
    },
    handleSelectionChange(val) {
      this.selectData = val;
    },
    initialize(page) {
      let params = {
        page: page ? page - 1 : this.currpage,
        size: this.pagesize,
        year: this.queryForm.year, //
        dimension: this.queryForm.dimension, //维度
        indexName: this.queryForm.index_name, //指标名称
        strategyTargetName: this.queryForm.strategy_target_name, //战略指标名称
        theme: this.queryForm.theme,
        dutyDep: this.queryForm.duty_dep,
        reportDep: this.queryForm.report_dep,
        isRelease: this.queryForm.is_release,
        isDelete: "0",
        sort: "id,desc",
      };
      this.tableLoading = true;
      crudMeasure
        .query(params)
        .then((res) => {
          this.tableLoading = false;
          if (res.success) {
            this.tableData = res.data[0].data;
            this.totalElements = res.data[0].totalElements;
          }
        })
        .catch((err) => {
          this.tableLoading = false;
        });
    },
    submitForm(formName) {},
    search() {
      this.initialize(1);
    },
    reset() {
      this.queryForm = {
        year: new Date().getFullYear() + "",
        dimension: "", //维度
        index_name: "", //指标名称
        strategy_target_name: "", //战略指标名称
        theme: "",
        stylduty_dep: "",
        sreport_dep: "",
        is_release: "",
      };
      this.initialize(1);
    },
    resetAdd() {
      this.year_month = new Date();
      this.form = {
        year_month: new Date(),
        year: "",
        month: "",
        dimension: "", //维度
        theme: "", //主题
        index_no: "", //指标序号
        index_name: "", //指标名称
        index_type: "", //指标类型
        strategy_target_name: "", //战略指标名称
        strategy_target_no: "", //战略指标序号
        duty_dep: this.bscDeptName, //责任部门
        report_dep: this.bscDeptName, //填报部门
        update_freq: "", //更新频率,
        formulaId: "",
        unit: "",
      };
    },
    clearFile() {
      const mainImg = this.$refs.upload;
      if (mainImg && mainImg.length) {
        mainImg.forEach((item) => {
          // item.uploadFiles.length = 0;
          item.clearFiles();
        });
      }
    },
    //导入
    uploadAction(params) {
      var data = new FormData();
      data.append("file", this.file.raw);
      data.append("year", this.uploadForm.uploadYearMonth.getFullYear());
      this.loading = true;
      // data.append('bscMonth', this.uploadForm.uploadYearMonth.getMonth() + 1)
      upload2(this.baseApi + "/api/bscMeasureIndexDep/upload", data)
        .then((res) => {
          this.loading = false;
          if (res.data.success) {
            this.$notify({
              title: "导入成功。",
              type: "success",
              duration: 2500,
            });
            this.cancel();
            this.initialize(1);
          } else {
            this.$notify({
              title: res.data.message,
              type: "error",
              duration: 2500,
            });
          }
        })
        .catch((err) => {
          this.loading = false;
          this.$alert(err.response.data.message, "错误提示", {
            confirmButtonText: "确定",
            dangerouslyUseHTMLString: true,
          });
        });
    },
    //上传控件改变事件
    onChange(file, fileList) {
      if (fileList) {
        this.uploadForm.fileList = fileList.slice(-1);
      }
      this.file = file;
    },
    // 上传文件
    upload() {
      if (this.$refs["uploadForm"]) {
        this.$refs["uploadForm"].validate((valid) => {
          if (valid) {
            if (!this.file || this.file.size === 0) {
              this.$notify({
                title: "请选择上传的文件",
                type: "error",
                duration: 2500,
              });
              return;
            }
            this.$refs.upload.submit();
          }
        });
      }
    },
    beforeUpload(file) {
      let isLt2M = true;
      isLt2M = file.size / 1024 / 1024 < 100;
      if (!isLt2M) {
        this.loading = false;
        this.$message.error("上传文件大小不能超过 100MB!");
      }
      this.form.name = file.name;
      return isLt2M;
    },
    handleSuccess(response, file, fileList) {
      this.$message.success("上传成功!");
      this.isUpload = false;
      this.initialize(1);
      this.clearFile();
      this.uploadForm.fileList = [];
    },
    // 监听上传失败
    handleError(e, file, fileList) {
      this.file = null;
      this.changeType = "error";
      const msg = JSON.parse(e.message);
      this.$notify({
        title: msg.message,
        type: "error",
        duration: 2500,
      });
      this.loading = false;
    },
    handleCurrentChange(val) {
      this.currpage = val;
      this.initialize(val);
    },
    cancel() {
      this.isUpload = false;
      this.clearFile();
      this.uploadForm.fileList = [];
    },
    editCancel() {
      this.isShow = false;
    },
    showUpload() {
      this.isUpload = true;
    },
  },
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.dashboard-editor-container {
  padding: 18px 22px 22px 22px;
  background-color: rgb(240, 242, 245);
  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}
.box-card {
  margin-bottom: 10px;
}
.flex-div {
  display: flex;
  justify-content: space-between;
}
.import {
  height: 28px;
  font-family: PingFangSC-Medium;
  text-decoration: underline;
  font-weight: normal;
  font-stretch: normal;
  letter-spacing: 0px;
  color: #2e99df;
}
</style>
