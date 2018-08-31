<template>
<section>
	<!--工具条-->
	<el-col class="toolbar" style="padding-bottom: 0px;">
		<el-form :inline="true" :model="filters">
			<el-form-item>
				<el-input v-model="filters.groupName" placeholder="用户组名称"></el-input>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" @click="getGroupList">查询</el-button>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" @click="showAddGroupDialog">新增</el-button>
			</el-form-item>
		</el-form>
	</el-col>

	<!--列表-->
	<el-table :data="groups" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
		<el-table-column type="selection" width="55">
		</el-table-column>
		<el-table-column type="index" width="160">
		</el-table-column>
		<el-table-column prop="groupId" label="用户组ID" width="220" sortable>
		</el-table-column>
		<el-table-column prop="groupName" label="用户组名称" width="220" sortable>
		</el-table-column>
		<el-table-column label="操作" width="180">
			<template slot-scope="scope">
				<el-button size="small" @click="showEditGroupDialog(scope.$index, scope.row)">编辑</el-button>
				<el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
			</template>
		</el-table-column>
	</el-table>

	<!--工具条-->
	<el-col :span="24" class="toolbar">
		<!-- <el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0">批量删除</el-button> -->
		<el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="20" :total="total" style="float:right;">
		</el-pagination>
	</el-col>

	<!--编辑界面-->
	<el-dialog title="编辑" v-model="editFormVisible" :close-on-click-modal="false">
		<el-form :model="editForm" label-width="96px" :rules="editFormRules" ref="editForm">
			<el-form-item label="用户组名称" prop="groupName">
				<el-input v-model="editForm.groupName" auto-complete="off"></el-input>
			</el-form-item>
			<el-form-item label="描述">
				<el-input type="textarea" v-model="editForm.addr"></el-input>
			</el-form-item>
		</el-form>
		<div slot="footer" class="dialog-footer">
			<el-button @click.native="editFormVisible = false">取消</el-button>
			<el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
		</div>
	</el-dialog>

	<!--新增界面-->
	<el-dialog title="新增用户组" v-model="addFormVisible" :close-on-click-modal="false">
		<el-form :model="addForm" label-width="96px" :rules="addFormRules" ref="addForm">
			<el-form-item label="用户组名称" prop="groupName">
				<el-input v-model="addForm.groupName" auto-complete="off"></el-input>
			</el-form-item>
			<el-form-item label="描述">
				<el-input type="textarea" v-model="addForm.addr"></el-input>
			</el-form-item>
		</el-form>
		<div slot="footer" class="dialog-footer">
			<el-button @click.native="addFormVisible = false">取消</el-button>
			<el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
		</div>
	</el-dialog>
</section>
</template>

<script>
import util from "../../common/js/util";
import { getGroupList, addGroup, editGroup } from "../../api/api";

export default {
  data() {
    return {
      listLoading: false,
      total: 0,
      page: 1,
      groups: [],
      filters: {
        groupName: ""
      },
      sels: [], //列表选中列

      addFormVisible: false, //新增界面是否显示
      addLoading: false,
      addFormRules: {
        groupName: [
          { required: true, message: "请输用户组名", trigger: "blur" },
          {
            pattern: /^[\u4E00-\u9FA5]+$/,
            message: "用户组名只能为中文"
          }
        ]
      },
      addForm: {
        groupName: ""
      },

      editFormVisible: false, //编辑界面是否显示
      editLoading: false,
      editFormRules: {
        groupName: [
          { required: true, message: "请输用户组名", trigger: "blur" }
        ]
      },
      editForm: {
        groupName: ""
      }
    };
  },
  methods: {
    handleCurrentChange(val) {
      this.page = val;
      this.getGroupList();
    },
    getGroupList() {
      this.listLoading = true;
      getGroupList("")
        .then(res => {
          this.listLoading = false;
          this.groups = res;
          this.total = res.length;
        })
        .catch(error => {
          console.info(error);
        });
    },
    selsChange(sels) {
      this.sels = sels;
    },
    //显示编辑界面
    showEditGroupDialog(index, row) {
      this.editFormVisible = true;
      this.editForm = Object.assign({}, row);
    },
    //显示新增界面
    showAddGroupDialog() {
      this.addFormVisible = true;
      this.addForm = {
        groupName: ""
      };
    },
    editSubmit() {
      this.$refs.editForm.validate(valid => {
        if (valid) {
          this.$confirm("确认提交吗？", "提示", {}).then(() => {
            this.addLoading = true;
            let para = Object.assign({}, this.editForm);
            let params = "groupId=" + para.groupId + "&name=" + para.groupName;
            // console.info(params);
            editGroup(params)
              .then(res => {
                this.addLoading = false;
                this.$message({
                  message: "提交成功",
                  type: "success"
                });
                this.$refs["editForm"].resetFields();
                this.editFormVisible = false;
                this.getGroupList();
              })
              .catch(error => {
                this.addLoading = false;
                this.$message({
                  message: "网络错误",
                  type: "error"
                });
              });
          });
        }
      });
    },
    addSubmit() {
      this.$refs.addForm.validate(valid => {
        if (valid) {
          this.$confirm("确认提交吗？", "提示", {}).then(() => {
            this.addLoading = true;
            let para = Object.assign({}, this.addForm);
            let params = "name=" + para.groupName;
            console.info(params);
            addGroup(params)
              .then(res => {
                this.addLoading = false;
                this.$message({
                  message: "提交成功",
                  type: "success"
                });
                this.$refs["addForm"].resetFields();
                this.addFormVisible = false;
                this.getGroupList();
              })
              .catch(error => {
                this.addLoading = false;
                this.$message({
                  message: "网络错误",
                  type: "error"
                });
              });
          });
        }
      });
    },
    handleCurrentChange() {}
  },
  mounted() {
    this.getGroupList();
  }
};
</script>