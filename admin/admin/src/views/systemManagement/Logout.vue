<template>
	<section>
		<el-button type="primary" style="margin:20px;" v-on:click="onLogout">退出登录</el-button>
	</section>
</template>

<script>
export default {
  methods: {
    onLogout() {
      var _this = this;
      this.$confirm("确认退出吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        // type: "warning"
      })
        .then(() => {
          sessionStorage.removeItem("user");
          _this.$router.push("/login");
        })
        .catch(() => {});
    }
  }
};
</script>

