<template>
  <section>
    <el-form :rules="formRules" ref="form" :model="form" label-width="80px" style="margin:20px;width:60%;min-width:600px;">
      <el-form-item label="原始密码" prop="oldPwd">
        <el-input type="password" v-model="form.oldPwd"></el-input>
      </el-form-item>
      <el-form-item label="新密码" prop="newPwd1">
        <el-input type="password" v-model="form.newPwd1"></el-input>
      </el-form-item>
      <el-form-item label="密码确认" prop="newPwd2">
        <el-input type="password" v-model="form.newPwd2"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">修改</el-button>
      </el-form-item>
    </el-form>
  </section>
</template>

<script>
import util from "../../common/js/util";

export default {
  data() {
    return {
      form: {
        oldPwd: "12"
      },
      formRules: {
        oldPwd: [
          { required: true, message: "请输密码", trigger: "blur" },
          { min: 6, max: 20, message: "长度6到20" }
        ],
        newPwd1: [
          { required: true, message: "请输新密码", trigger: "blur" },
          { min: 6, max: 20, message: "长度6到20" }
        ],
        newPwd2: [
          { required: true, message: "请输确认密码", trigger: "blur" },
          {
            validator: (rule, value, callback) => {
              if (value === "") {
                callback(new Error("请输入确认密码"));
              } else if (value !== this.form.newPwd1) {
                callback(new Error("两次输入密码不一致!"));
              } else {
                callback();
              }
            }
          }
        ]
      }
    };
  },
  methods: {
    onSubmit: function() {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.$confirm("确认修改吗？", "提示", {}).then(() => {
            let params = Object.assign({}, this.form);
            let pStr =
              "oldPwd=" +
              params.oldPwd +
              "&newPwd1=" +
              params.newPwd1 +
              "&newPwd2=" +
              params.newPwd2;
            alert(pStr);
          });
        }
      });
    }
  }
};
</script>