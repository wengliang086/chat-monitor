<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="filters">
				<el-form-item>
					<el-input v-model="filters.msg" placeholder="可疑信息"></el-input>
				</el-form-item>
								
				<el-form-item>
					<el-select v-model="value8" filterable clearable placeholder="游戏" @change="getGame">
						<el-option
						v-for="(item,index) in gameList"
						:key="index"
						:label="item.gameName"
						:value="item.gameId">
						</el-option>
					</el-select>
				</el-form-item>

				<el-form-item>
					<el-button type="primary" v-on:click="getSuspiciousList">查询</el-button>
				</el-form-item>

			</el-form>
		</el-col>

		<!--列表-->
		<el-table :data="suspiciousData" highlight-current-row v-loading="listLoading" 
		
		 @selection-change="selsChange" style="width: 100%;">
			<el-table-column type="selection" width="55">
			</el-table-column>
		    <el-table-column type="index" width="60">
			</el-table-column>
			<!--<el-table-column prop="id" label="id" width="120" sortable>
			</el-table-column>-->
			<el-table-column prop="msg" label="可疑信息" width="120" sortable>
			</el-table-column>
			<el-table-column prop="illegalWords" label="可疑词" width="120" sortable>
			</el-table-column>
			<el-table-column prop="uid" label="uid" width="120" sortable>
			</el-table-column>
			<el-table-column prop="gameUid" label="游戏uid" width="120" sortable>
			</el-table-column>
			<el-table-column prop="gameName" label="游戏" width="120" sortable>
			</el-table-column>
			<el-table-column prop="statusDes" label="状态描述" width="120" sortable>
			</el-table-column>
			<!--<el-table-column prop="opUid" label="审核人id" width="120" sortable>
			</el-table-column>-->
			<el-table-column prop="opAccount" label="审核人" width="120" sortable>
			</el-table-column>
			<el-table-column prop="createTime" label="创建时间" width="120" sortable>
			</el-table-column>

			
			<el-table-column label="操作" width="180">
				<template slot-scope="scope" >
					<el-button size="small" @click="handleEdit(scope.$index, scope.row,1)" v-if="Object.assign({},scope.row).status==null">审核</el-button>
					<el-button size="small" @click="handleEdit(scope.$index, scope.row,0)" v-else>查看</el-button>
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
		<el-dialog title="审核" v-model="editFormVisible" :close-on-click-modal="false">
			<el-form :model="editForm" label-width="80px"  :rules="editFormRules" ref="editForm">
				<el-form-item label="游戏">
					<el-input v-model="editForm.gameName" auto-complete="off" :disabled=true></el-input>
				</el-form-item>
				<el-form-item label="可疑信息">
					<el-input type="textarea" v-model="editForm.msg" auto-complete="off"  :disabled=true></el-input>
				</el-form-item>
				<el-form-item label="可疑词" prop="illegalWords">
					<el-input type="textarea" v-model="editForm.illegalWords" placeholder="多个可疑词用逗号隔开"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="editFormVisible = false">取消</el-button>
				<el-button v-if="editForm.status==null"  type="primary" @click.native="editSubmit(-1)" :loading="editLoading">违规</el-button>
				<el-button v-if="editForm.status==null"  type="primary" @click.native="editSubmit(1)">正常</el-button>
			</div>
		</el-dialog>

	</section>
</template>

<script>
	import util from '../../common/js/util'
	//import NProgress from 'nprogress'
	import {getSuspiciousList,editSuspicious,getGameListPage } from '../../api/api';

	export default {
		data() {
			return {
				filters: {msg:'',gameId:0},
				suspiciousData: [],
				total: 0,
				page: 1,
				listLoading: false,
				sels: [],//列表选中列
				
				editFormVisible: false,//编辑界面是否显示
				editLoading: false,
				editFormRules: {
					illegalWords: [{ required: false, message: '可疑信息确定违规时必须填写可疑词'}]
				},

				//编辑界面数据
				editForm: {
					id: 0,
					gameName: '',
					msg: '',
					illegalWords: '',
					status:1
				
				},

				gameList: null,/**[{gameId: '1000',gameName: '焚天'}, {gameId: '1001',gameName: '勇者大作战'}}]*/
				value8:'',
				//owIndex: '-1',
				//OrderIndexArr: [],
				//hoverOrderArr: []

			}
		},

		methods: {
			
			handleCurrentChange(val) {
				this.page = val;
				this.getSuspiciousList();
			},
			//获取用户列表
			getSuspiciousList() {
				let para = {
					page: this.page,
					msg: this.filters.msg,
					gameId:this.filters.gameId
				};
				this.listLoading = true;
				//NProgress.start();
				getSuspiciousList(para).then((res) => {
					this.total =res==null?0:res.length;
					this.suspiciousData = res;
					this.listLoading = false;
					//NProgress.done();
				});
			},
			
			selsChange: function (sels) {
				this.sels = sels;
			},

			//显示编辑界面
			handleEdit: function (index, row,action) {				
				this.editFormVisible = true;
				this.editForm = Object.assign({}, row);
			},
			getGame:function(option){				
					this.filters.gameId=option;
					this.getSuspiciousList();
			},

			editSubmit:function(isIllegaFlag) {
				this.$refs.editForm.validate(valid => {
									
					if(isIllegaFlag==-1 && (this.editForm.illegalWords==null || /^\s*$/g.test(this.editForm.illegalWords) == true)) {						
						//console.log("isIllegaFlag="+isIllegaFlag + "illegalWords=null or '' ");
					
						this.editFormRules.illegalWords[0].required=true;
						this.$refs.editForm.validate();
						return false;
					
					}else{
						
						//提交表单
						this.editLoading = true;
						let para = Object.assign({}, this.editForm);
						let params = "suspiciousId=" + para.id;
						params=isIllegaFlag==1?params:params+"&illegalWords=" + para.illegalWords ;
						console.info("editSubmit property="+params);						

						editSuspicious(params).then((res) => {
								this.editLoading = false;
								this.$message({
									message: '提交成功',
									type: 'success'
								});
								this.$refs['editForm'].resetFields();
								this.editFormVisible = false;
								this.getSuspiciousList();

								this.editFormRules.illegalWords[0].required=false;
								this.$refs.editForm.validate();	
							});						
					}
					
					
				});
			},

			getGameListPage:function(){//获取当前用户所在用户组下的所有游戏
				this.listLoading = true;
				getGameListPage().then((res) => {				
					this.gameList=res;
					this.listLoading = false;
				});
			},

			//cellMouseEnter: function (row, column, cell, event) {
					/*this.rowIndex = row.rowIndex
					this.hoverOrderArr = []
					this.OrderIndexArr.forEach(function (element) {
					if (element.indexOf(this.rowIndex) >= 0) {
						this.hoverOrderArr = element
					}
					}, this);*/
					//return cell.val;
      		//},

			//cellMouseLeave: function (row, column, cell, event) {
				//this.rowIndex = '-1'
			//}


		},
		mounted() {
			this.getSuspiciousList();
			this.getGameListPage();
		}

	

	}

</script>

<style scoped>

</style>