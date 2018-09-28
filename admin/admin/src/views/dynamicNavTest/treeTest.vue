<template>
<div>
    <el-card class="el-card">
        <el-row>
            <el-button type="primary" icon="el-icon-plus">添加子部门</el-button>
            <el-button icon="el-icon-plus">添加一级部门</el-button>
            <el-button icon="el-icon-delete">批量删除</el-button>
            <el-button icon="el-icon-refresh">刷新</el-button>
        </el-row>
        <el-row :gutter="20" style="margin-top: 15px;">
            <el-col :span="6">
                <el-alert title="当前选择编辑：" show-icon :closable="false">
                    <span>已选择部门</span>
                    <a href="">取消选择</a>
                </el-alert>
                <el-tree :data="data" style="margin-top: 15px;"></el-tree>
            </el-col>
            <el-col :span="9" type="flex">
                <el-form label-width="85" :inline="false">
                    <el-form-item label="上级部门">
                        <el-input type="text" />
                    </el-form-item>
                    <el-form-item label="部门名称">
                        <el-input type="text" />
                    </el-form-item>
                    <el-form-item label="排序值">
                        <el-input type="text" />
                    </el-form-item>
                    <el-form-item label="是否启用">
                        <el-input type="text" />
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary">修改并保存</el-button>
                        <el-button>重置</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
    </el-card>
</div>
</template>

<script>
export default {
    data() {
        return {
            data: [{
                label: '一级 1',
                children: [{
                    label: '二级 1-1',
                    children: [{
                        label: '三级 1-1-1'
                    }]
                }]
            }, {
                label: '一级 2',
                children: [{
                    label: '二级 2-1',
                    children: [{
                        label: '三级 2-1-1'
                    }]
                }, {
                    label: '二级 2-2',
                    children: [{
                        label: '三级 2-2-1'
                    }]
                }]
            }, {
                label: '一级 3',
                children: [{
                    label: '二级 3-1',
                    children: [{
                        label: '三级 3-1-1'
                    }]
                }, {
                    label: '二级 3-2',
                    children: [{
                        label: '三级 3-2-1'
                    }]
                }]
            }]
        }
    }
}
</script>

<style scoped>
.el-card {
    margin-top: 12px;
    margin-left: 0px;
}
</style>
