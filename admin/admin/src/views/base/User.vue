<template>
<section>
	<!--工具条-->
	<el-col class="toolbar" style="padding-bottom: 0px;">
		<el-form :inline="true" :model="filters">
			<el-form-item>
				<el-input v-model="filters.userName" placeholder="用户名称"></el-input>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" @click="getUserList">查询</el-button>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" @click="showAddUserDialog">新增</el-button>
			</el-form-item>
		</el-form>
	</el-col>

	<!--列表-->
	<el-table :data="users" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
		<el-table-column type="selection" width="55">
		</el-table-column>
		<el-table-column type="index" width="60">
		</el-table-column>
		<el-table-column prop="uid" label="用户ID" width="120" sortable>
		</el-table-column>
		<el-table-column prop="account" label="用户名称" width="120" sortable>
		</el-table-column>
		<el-table-column prop="group_id" label="所属组ID" width="120" sortable>
		</el-table-column>
		<el-table-column prop="group_name" label="所属组" width="120" sortable>
		</el-table-column>
		<el-table-column prop="phone" label="手机号码" width="160" sortable>
		</el-table-column>
		<el-table-column prop="email" label="邮箱地址" width="220" sortable>
		</el-table-column>
		<el-table-column label="操作" width="180">
			<template slot-scope="scope">
				<el-button size="small" @click="showEditUserDialog(scope.$index, scope.row)">编辑</el-button>
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
		<el-form :inline="true" :model="editForm" label-width="96px" :rules="editFormRules" ref="editForm">
			<el-form-item label="用户名称" prop="account">
				<el-input v-model="editForm.account" auto-complete="off"></el-input>
			</el-form-item>
			<el-form-item label="手机号码" prop="phone">
				<el-input v-model="editForm.phone" auto-complete="off"></el-input>
			</el-form-item>
			<el-form-item label="邮箱地址" prop="email">
				<el-input v-model="editForm.email" auto-complete="off"></el-input>
			</el-form-item>
			<el-form-item label="密码" prop="pwd1">
				<el-input type="password" v-model="editForm.pwd1" auto-complete="off"></el-input>
			</el-form-item>
			<el-form-item label="密码确认" prop="pwd2">
				<el-input type="password" v-model="editForm.pwd2" auto-complete="off"></el-input>
			</el-form-item>
			<el-form-item label="是否管理员">
				<el-switch v-model="editForm.admin"></el-switch>
			</el-form-item>
			<el-form-item label="所属用户组">
				<el-select v-model="editForm.selected" placeholder="请选择用户组">
					<el-option v-for="(item, index) in editForm.groupArr" :label="item.group_name" :value="item.group_id" :key="index"></el-option>
				</el-select>
			</el-form-item>
		</el-form>
		<div slot="footer" class="dialog-footer">
			<el-button @click.native="editFormVisible = false">取消</el-button>
			<el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
		</div>
	</el-dialog>

	<!--新增界面-->
	<el-dialog title="新增用户" v-model="addFormVisible" :close-on-click-modal="false">
		<el-form :inline="true" :model="addForm" label-width="96px" :rules="addFormRules" ref="addForm" size="mini">
			<el-form-item label="用户名称" prop="account">
				<el-input v-model="addForm.account" auto-complete="off"></el-input>
			</el-form-item>
			<el-form-item label="手机号码" prop="phone">
				<el-input v-model="addForm.phone" auto-complete="off"></el-input>
			</el-form-item>
			<el-form-item label="邮箱地址" prop="email">
				<el-input v-model="addForm.email" auto-complete="off"></el-input>
			</el-form-item>
			<el-form-item label="密码" prop="pwd1">
				<el-input type="password" v-model="addForm.pwd1" auto-complete="off"></el-input>
			</el-form-item>
			<el-form-item label="密码确认" prop="pwd2">
				<el-input type="password" v-model="addForm.pwd2" auto-complete="off"></el-input>
			</el-form-item>
			<el-form-item label="是否管理员">
				<el-switch v-model="addForm.admin"></el-switch>
			</el-form-item>
			<el-form-item label="所属用户组">
				<el-select v-model="addForm.selected" placeholder="请选择用户组">
					<el-option v-for="(item, index) in addForm.groupArr" :label="item.group_name" :value="item.group_id" :key="index"></el-option>
				</el-select>
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
import { getUserList, addUser, editUser } from "../../api/api";

export default {
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.addForm.pwd1) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      listLoading: false,
      total: 0,
      page: 1,
      users: [],
      filters: {
        account: ""
      },
      sels: [], //列表选中列

      addFormVisible: false, //新增界面是否显示
      addLoading: false,
      addFormRules: {
        account: [
          { required: true, message: "请输用户名", trigger: "blur" },
          { min: 6, max: 20, message: "长度6到20" }
        ],
        phone: [
          { required: true, message: "请输入手机号码", trigger: "blur" },
          // { pattern: /^1[34578]\d{9}$/, message: "请输入正确的手机号" },
          {
            validator: function(rule, value, callback) {
              if (/^1[34578]\d{9}$/.test(value) == false) {
                callback(new Error("请输入正确的手机号1"));
              } else {
                callback();
              }
            },
            trigger: "blur"
          }
        ],
        email: [
          {
            pattern: /\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/,
            message: "请输入正确的邮箱地址"
          }
        ],
        pwd1: [
          { required: true, message: "请输密码", trigger: "blur" },
          { min: 6, max: 20, message: "长度6到20" }
        ],
        pwd2: [
          { required: true, message: "再次确认密码", trigger: "blur" },
          { validator: validatePass }
        ]
      },
      addForm: {
        account: "",
        admin: false,
        groupArr: [],
        pwd1: "",
        pwd2: "",
        selected: null
      },

      editFormVisible: false, //编辑界面是否显示
      editLoading: false,
      editFormRules: {
        account: [
          { required: true, message: "请输用户名", trigger: "blur" },
          { min: 6, max: 20, message: "长度6到20" }
        ],
        phone: [
          { required: true, message: "请输入手机号码", trigger: "blur" },
          { pattern: /^1[34578]\d{9}$/, message: "请输入正确的手机号" }
        ],
        email: [
          {
            pattern: /\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/,
            message: "请输入正确的邮箱地址"
          }
        ]
      },
      editForm: {
        account: "",
        admin: false,
        groupArr: [],
        pwd1: "",
        pwd2: "",
        selected: null
      }
    };
  },
  methods: {
    handleCurrentChange(val) {
      this.page = val;
      this.getUserList();
    },
    getUserList() {
      this.listLoading = true;
      getUserList("")
        .then(res => {
          this.listLoading = false;
          this.users = res;
          this.total = res.length;

          var map = new Map();
          this.users.forEach(element => {
            map.set(element.group_id, element.group_name);
          });
          //   console.log(map);
          map.forEach((value, key, mapObj) => {
            // console.log(value + "---" + key + "---" + mapObj);
            if (key) {
              this.addForm.groupArr.push({ group_id: key, group_name: value });
            }
          });
          //   console.log(this.addForm.groupArr);
        })
        .catch(error => {
          console.info(error);
        });
    },
    selsChange(sels) {
      this.sels = sels;
    },
    //显示编辑界面
    showEditUserDialog(index, row) {
      this.editFormVisible = true;
      this.editForm = Object.assign({}, row);
      this.editForm.groupArr = this.addForm.groupArr;
      // 小面两种写法，第一种是错误的，这里必须使用$set()赋值
      // this.editForm.selected = this.editForm.group_id;
      this.$set(this.editForm, "selected", this.editForm.group_id);
      if (!this.editForm.group_id) {
        // this.editForm.admin = true;
        this.$set(this.editForm, "admin", true);
      } else {
        // this.editForm.admin = false;
        this.$set(this.editForm, "admin", false);
      }
    },
    //显示新增界面
    showAddUserDialog() {
      this.addFormVisible = true;
    },
    editSubmit() {
      this.$refs.editForm.validate(valid => {
        if (valid) {
          this.$confirm("确认提交吗？", "提示", {}).then(() => {
            this.addLoading = true;
            let para = Object.assign({}, this.editForm);
            let params = "userId=" + para.userId + "&name=" + para.userName;
            // console.info(params);
            editUser(params)
              .then(res => {
                this.addLoading = false;
                this.$message({
                  message: "提交成功",
                  type: "success"
                });
                this.$refs["editForm"].resetFields();
                this.editFormVisible = false;
                this.getUserList();
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
            let params =
              "account=" +
              para.account +
              "&phone=" +
              para.phone +
              "&password=" +
              para.pwd1 +
              "&groupId=" +
              para.selected;
            if (para.email) {
              params += "&email=" + para.email;
            }
            console.info(params);
            addUser(params)
              .then(res => {
                this.addLoading = false;
                this.$message({
                  message: "提交成功",
                  type: "success"
                });
                this.$refs["addForm"].resetFields();
                this.addFormVisible = false;
                this.getUserList();
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
    this.getUserList();
  }
};
</script>
