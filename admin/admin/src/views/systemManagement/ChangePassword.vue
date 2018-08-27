<template>
  <el-form ref="form" :model="form" label-width="80px" style="margin:20px;width:60%;min-width:600px;">
    <el-form-item label="原始密码">
      <el-input v-model="form.name"></el-input>
    </el-form-item>
    <el-form-item label="新密码">
      <el-input v-model="form.newPwd1"></el-input>
    </el-form-item>
    <el-form-item label="密码确认">
      <el-input v-model="form.newPwd2"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">修改</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  data() {
    return {
      form: {
        name: ""
      }
    };
  },
  methods: {
    onSubmit: function() {
      alert('1231');
    }
  }
};
</script>