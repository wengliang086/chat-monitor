<template>
<el-col :span="24" class="header">
    <el-col :span="10" class="logo" :class="collapsed?'logo-collapse-width':'logo-width'">
        <img src="../../assets/hoolai_logo.png" alt="12">
        {{collapsed?'':subSysName}}
    </el-col>
    <el-col :span="10">
        <div class="tools" @click.prevent="collapse">
            <i class="fa fa-align-justify"></i>
        </div>
    </el-col>
    <el-col :span="4" class="userinfo">
        <el-dropdown trigger="hover">
            <span class="el-dropdown-link userinfo-inner"><img :src="this.sysUserAvatar" /> {{sysUserName}}</span>
            <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>我的消息</el-dropdown-item>
                <el-dropdown-item>设置</el-dropdown-item>
                <el-dropdown-item divided @click.native="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
    </el-col>
</el-col>
</template>

<script>
export default {
    // 父向子传值
    // props: ["subSysName"],
    props: {
        subSysName: {
            type: String,
            required: true
        }
    },
    data() {
        return {
            collapsed: false,
            sysUserName: "",
            sysUserAvatar: ""
        };
    },
    //折叠导航栏
    methods: {
        collapse: function () {
            this.collapsed = !this.collapsed;
            this.$emit("collapseChanged", this.collapsed);
        },
        //退出登录
        logout: function () {
            var _this = this;
            this.$confirm("确认退出吗?", "提示", {
                    //type: 'warning'
                })
                .then(() => {
                    sessionStorage.removeItem("user");
                    _this.$router.push("/login");
                })
                .catch(() => {});
        }
    },
    mounted() {
        var user = sessionStorage.getItem("user");
        if (user) {
            user = JSON.parse(user);
            this.sysUserName = user.account || "";
            this.sysUserAvatar =
                user.avatar ||
                "https://raw.githubusercontent.com/taylorchen709/markdown-images/master/vueadmin/user.png";
        }
    }
};
</script>

<style lang="scss" scoped>
@import "~scss_vars";

.header {
    height: 60px;
    line-height: 60px;
    background: $color-primary;
    color: #fff;

    .userinfo {
        text-align: right;
        padding-right: 35px;
        float: right;

        .userinfo-inner {
            cursor: pointer;
            color: #fff;

            img {
                width: 40px;
                height: 40px;
                border-radius: 20px;
                margin: 10px 0px 10px 10px;
                float: right;
            }
        }
    }

    .logo {
        //width:230px;
        height: 60px;
        font-size: 22px;
        padding-left: 10px;
        padding-right: 10px;
        border-color: rgba(238, 241, 146, 0.3);
        border-right-width: 1px;
        border-right-style: solid;

        img {
            width: 30px;
            float: left;
            margin: 20px 5px 10px 5px;
        }

        .txt {
            color: #fff;
        }
    }

    .logo-width {
        width: 230px;
    }

    .logo-collapse-width {
        width: 60px;
    }

    .tools {
        padding: 0px 23px;
        width: 14px;
        height: 60px;
        line-height: 60px;
        cursor: pointer;
    }
}
</style>
