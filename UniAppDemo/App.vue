<script>
	export default {
		onLaunch: function() {
			console.log('App Launch')
			
			// #ifdef APP-PLUS			
				this.checkArguments(); // 检测启动参数 // 这是默认的监听参数 也就是应用初始化的时候监听  
				
				// 监听后台恢复 这是利用5+的方式 处理 APP进入后台后 再进入到APP前台时参数监听  
				plus.globalEvent.addEventListener('newintent', (e)=>{  
					this.checkArguments(); // 检测启动参数  
				});			
				
				plus.push.addEventListener('click', function(msg){  
					console.log('plus.push click');
					console.log('plus.push click payload =>'+msg.payload);
				    //处理点击消息的业务逻辑代码  
				}, false); 
			// #endif  
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		},
		methods: {
			checkArguments() {  
				try {  
					var plusJson = JSON.parse(plus.runtime.arguments);  
					console.log('plusJson.phone: ' + plusJson.phone);  //[LOG]Shortcut-plus.runtime.arguments: {"name":"Tiger","birthday":"1983-7-13"}
					
					uni.setStorageSync('phone',plusJson.phone);
				} catch (e) {  
					console.log('Shortcut-exception: ' + e);  
				}  
			}, 
		}
	}
</script>

<style>
	/*每个页面公共css */
</style>
