<template>
	<section>
		<el-button type="primary" style="margin:20px;" v-on:click="onLogout">退出登录</el-button>
	</section>
</template>

<script>
export default {
	methods: {
		onLogout() {
			alert('ddd');
		}
	}
}
</script>

