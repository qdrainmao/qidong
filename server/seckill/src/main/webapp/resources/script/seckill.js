//存放主要交互逻辑js代码
// javascript模块化
var seckill = {
    //封装秒杀相关ajax的url
    URL : {
        now : function () {
            return '/seckill/time/now';
        },
        exposer : function (seckillId) {
            return '/seckill/' + seckillId + '/exposer';
        },
        execution : function (seckillId,md5) {
            return '/seckill/'+seckillId+'/'+md5+'/execution';
        }
    },
    handleSeckill : function (seckillId, node) {
        //处理秒杀逻辑
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
        $.post(seckill.URL.exposer(seckillId),{},function (result) {
            if (result && result['success']){
                var exposer = result['data'];
                if (exposer['exposed']){
                    //开启秒杀
                    //获取秒杀地址
                    var md5 = exposer['md5'];
                    var killUrl = seckill.URL.execution(seckillId,md5);
                    //只绑定一次点击事件
                    $('#killBtn').one('click', function () {
                        //执行秒杀请求
                        //1先禁用按钮
                        $(this).addClass('disabled');
                        //2发送秒杀请求
                        $.post(killUrl,{},function (result) {
                            if (result && result['success']){
                                var killResult = result['data'];
                                var state = killResult['state'];
                                var stateInfo = killResult['stateInfo'];
                                //3显示秒杀结果
                                node.html('<span class="label label-success">'+stateInfo+'</span>');
                            }
                        });
                    });
                    node.show();
                }else {
                    //未开启秒杀
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    //todo 重新计算计时逻辑
                }

            }else {
                console.log('result:' + result);
            }
        })
    },
    validatePhone : function (phone) {
        if (phone && phone.length == 11 && !isNaN(phone)){
            return true;
        }else {
            return false;
        }
    },
    countdown2 : function (seckillId, nowTime, startTime, endTime) {
        var seckillBox = $('#seckill-box');
        if (nowTime > endTime){
            seckillBox.html('秒杀结束！');
        }else if(nowTime < startTime){
            var killTime = new Date(startTime + 1000);
            //todo
            seckillBox.countdown(killTime, function (event) {
                var format = event.strftime('秒杀倒计时： %D天 %H时 %M分 %S秒');
                seckillBox.html(format);
            })
        }else {
            seckill.handleSeckill(seckillId, seckillBox)
        }

    },
    //详情页秒杀逻辑
    detail : {
        //详情页初始化
        init : function (params) {
            // $('.modal-backdrop.in').css('display','none');
            //用户手机验证和登录，计时交互
            //规划我们的交互流程
            //没有使用后端，就在cookie中查找手机号
            var killPhone = $.cookie('killPhone');
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];
            //验证手机号
            if (!seckill.validatePhone(killPhone)){
                //绑定phone
                //控制输出
                $('#killPhoneBtn').click(function () {
                    var inputPhone = $('#killPhoneKey').val();
                    if (seckill.validatePhone((inputPhone))){
                        //电话写入cookie，只在/seckill路径下cookie才生效
                        $.cookie('killPhone',inputPhone,{expires:7,path:'/seckill'});
                        //刷新页面
                        window.location.reload();
                    }else {
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误！</label>').show(300);
                    }
                });
            }else {
                //已经登录
                var killPhoneModal = $('#killPhoneModal').addClass('modal fade');;
                killPhoneModal.modal({
                    show:false
                });

                //计时交互
                $.get(seckill.URL.now(),{},function (result) {
                    if (result && result['success']){
                        var nowTime = result['data'];
                        //时间判断
                        seckill.countdown2(seckillId,nowTime,startTime,endTime);
                    }else {
                        console.log('result:'+result)
                    }
                })
            }
        }
    }
}