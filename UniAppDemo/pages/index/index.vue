<template>
	<view class="container">
		<view class="main" v-if="!loginStatus">
			<view class="input-box">
				<view class="input">
					<input type="number" :placeholder="$t('message.inputPhone')" v-model="phone" placeholder-class="input-place"></input>
					<view class="input-err">
						{{phoneErr}}
					</view>
				</view>
			</view>
			<view class="input-box">
				<view class="input" style="border: none;">
					<input type="number" :placeholder="$t('message.inputCode')" class="code-input" v-model="messagecode" placeholder-class="input-place" />
					<view class="input-err">
						{{codeErr}}
					</view>
				</view>
				<view class="code-btn-box">
					<button type="default" @click="sendCode">{{$t('message.sendText')}}</button>
					<!-- <button slot="button" round color='#4980F9' size="small" :disabled="sendDisabled" type="primary"></button> -->
				</view>
			</view>
		</view>
		<view class="btn-row" v-if="!loginStatus">
			<button class="collectData" round color='#4980F9' type="primary" size="large" :disabled="enterDisabled" @click="getWxOpenid">{{ $t('message.binding') }}</button>
		</view>
		<view class="btn-row">
			<switchs text="EN|CN" @change="switchChange" />
		</view>
	</view>		
</template>

<script>	
	import Switchs from '../../components/Switchs.vue'
	export default {
		components: {
		      Switchs
		    },
		data() {
			return {
				phoneErr: "", //手机号错误提示
				codeErr: '',
				messagecode: '',
				phone: '',
				sendText: this.$t('message.sendText'),
				enterDisabled: false,
				loginStatus:false
			}
		},
		onLoad() {
			console.log('App onLoad()');
			this.phone = uni.getStorageSync("phone");
			console.log('App onLoad() phone ==> '+ this.phone);
		},
		methods: {
			//发送验证码倒计时
			sendCode() {
				if (!(/^1[0-9][0-9]\d{4,8}$/.test(this.phone))) {
					uni.showToast({
						title: '请输入正确的手机号',
						icon: 'none'
					})
					return
				}
				this.messagecode = "546187";
				this.enterDisabled = false;
			},
			
			//获取用户openid
			getWxOpenid() {
				if (!(/^1[0-9][0-9]\d{4,8}$/.test(this.phone))) {
					uni.showToast({
						title: '请输入正确的手机号',
						icon: 'none'
					})
					return
				}
				if(!this.messagecode){
					uni.showToast({
						title: '请输入验证码',
						icon: 'none'
					})
					return
				}
				uni.showToast({
					title: '绑定成功',
					icon: 'none'
				})
				this.enterDisabled = false;
				
				// #ifdef APP-PLUS					
					plus.push.createMessage("您已绑定成功!");				
				// #endif 
			},
			
			switchChange(e){
				if(e) {
					this.$i18n.locale = "cn";		
				} else {					
					this.$i18n.locale = "en";
				}
			}
		},
		watch: {
			"phone": {
				handler: function(val) {
					if (val.length == 0) {
						this.phoneErr = "手机号不能为空";
						this.sendDisabled = true;
						this.enterDisabled = true;
					} else if (val.length == 11) {
						this.phoneErr = "";
						this.sendDisabled = false;
						if ((this.messagecode.length == 4 || this.messagecode.length == 5 || this.messagecode.length == 6) && this.checked) {
							this.enterDisabled = false;
						}
					} else {
						this.phoneErr = "手机号格式错误";
						this.enterDisabled = true;
						this.sendDisabled = true;
					}
				},
				deep: true //深度监听
			},
			"messagecode": {
				handler: function(val) {
					if (val.length == 0) {
						this.codeErr = "验证码不能为空";
						this.enterDisabled = true;
					} else if (val.length == 4 ||val.length == 5 || val.length == 6) {
						this.codeErr = "";
						if (this.phone.length == 11 && this.checked) {
							this.enterDisabled = false;
						}
					} else {
						this.codeErr = "验证码格式错误";
						this.enterDisabled = true;
					}
				},
				deep: true //深度监听
			},
		}
	}
</script>

<style>
	.container {
		padding: 117rpx 19rpx 0;
	}

	.logo {
		margin: 0 auto;
	}

	.main {
		margin-top: 106rpx;
		width: 100%;
		background: rgba(255, 255, 255, 1);
		border: 1px solid rgba(240, 240, 240, 1);
		border-radius: 12rpx;
		overflow: hidden;
	}

	.input-box {
		width: 100%;
		padding: 0 30rpx;
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.input input {
		display: block;
		width: 100%;
		height: 104rpx;
		line-height: 104rpx;
	}
	.input{
		width: 100%;
		font-size: 30rpx;
		border-bottom: 1px solid #E9E9E9;
		position: relative;
	}
	.input-err{
		width: 100%;
		font-size: 20rpx;
		color: red;
		position: absolute;
		bottom: 8rpx;
		left: 0;
	}
	.code-input{
		width: 60%;
	}
	.code-btn-box{
		width: 40%;
		height: 100%;
		display: flex;
		align-items: center;
		justify-content: flex-end;
	}
	.input-place {
		font-size: 30rpx;
		font-weight: 400;
		color: #B9B9B9;
	}

	.agree {
		margin-top: 43rpx;
		padding-left: 74rpx;
	}

	.agree-box {
		display: flex;
		align-items: center;
		font-size: 22rpx;
		font-weight: 500;
		color: #5B5B5B;
	}

	.agreement {
		font-size: 22rpx;
		font-weight: 500;
		color: #4980F9;
	}

	.btn-row {
		margin-top: 59rpx;
	}
</style>
