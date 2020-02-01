//存放主要交互的代码
//javascript 模块化
var seckill = {
    //封装秒杀相关ajax的url
    URL:{
        now : function(){
            return bpath+'time/now';

        },
        exposer : function(seckillId){
            return bpath+seckillId+'/exposer';
        },
        execution : function(seckillId,md5){
            return bpath+seckillId+'/'+md5+'/'+'execution';
        }



    },

    //验证手机号
    validatePhone: function (phone) {
        if (phone && phone.length == 11 && !isNaN(phone)) {
            return true;//直接判断对象会看对象是否为空,空就是undefine就是false; isNaN 非数字返回true
        } else {
            return false;
        }
    },

    countDown: function(seckillId,nowTime,startTime,endTime){
        console.log(nowTime);
        console.log(startTime);
        var seckillBox = $('#seckill-box');
        //时间判断,
        //用户会频繁刷新页面，这里先过滤下时间，减少请求
        if(nowTime > endTime){
            //秒杀结束
            seckillBox.html("秒杀结束");
        }else if(nowTime < startTime){
            //秒杀未开始，计时
            var killTime = new Date(startTime);
            seckillBox.countdown(killTime,function(event){
                //时间格式
                var format = event.strftime('秒杀倒计时: %D天 %H时 %M分 %S秒 ');
                seckillBox.html(format);
                //时间完成后回调事件（秒杀开始）
            }).on('finsh.countdown',function(){
                //获取秒杀地址，控制显示逻辑，执行秒杀
                //会有无数人在这里刷新，等着秒杀时间开启，
                seckill.handlerSeckill(seckillId,seckillBox);
            }); //插件

        }else{
            //秒杀开始
            //无数人进行刷新页面，重复秒杀，并发量高
            seckill.handlerSeckill(seckillId,seckillBox);
        }
    },


    handlerSeckill: function(seckillId,node){
        //处理秒杀逻辑
        console.log('node: '+node);
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>"');

        //此处exposer 高并发量！
        $.post(seckill.URL.exposer(seckillId),{},function(result){
            //在回调函数中，执行交互流程

            if(result){
                var exposer = result['exposed'];
                if(exposer){
                    //开启秒杀
                    //获取秒杀地址
                    var md5 = result['md5'];
                    var killUrl = seckill.URL.execution(seckillId,md5); //秒杀地址
                    console.log("killUrl: "+killUrl);
                    //绑定一次点击事件
                    $('#killBtn').one('click',function(){
                        //执行秒杀请求的操作
                        //1.先禁用按钮，避免多次请求
                        $(this).addClass('disabled');
                        //2。发送秒杀请求
                        console.log("80  秒杀请求")
                        $.post(killUrl,{},function(result){ //post秒杀请求 携带 物品id， exposer生成的MD5
                            console.log(result);

                            node.html('<span class="label label-success">' + result['stateInfo'] + '</span>');
                        });
                    });

                    node.show();
                }else{
                    //未开启 电脑时间不准
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    seckill.countDown(seckillId,now,start,end);
                }
            }else{
                console.log("result"+result);
            }
        });
    },


    //详情页秒杀逻辑
    detail:{
        //详情页初始化
        init:function(params){
            //手机验证和登录，计时交互
            //规划交互流程
            //在cookie中查找手机号
            var killPhone = $.cookie('killPhone');
            console.log(killPhone);

            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];
            //验证手机号
            if(!seckill.validatePhone(killPhone)){

                //绑定phone
                //控制输出
                var killPhoneModal = $('#killPhoneModal');
                killPhoneModal.modal({
                    show: true,//显示弹出层
                    backdrop: 'static',//禁止位置关闭
                    keyboard: false//关闭键盘事件
                });
                console.log("到了");

                $('#killPhoneBtn').click(function(){
                    var inputPhone = $('#killPhoneKey').val();
                    console.log("有无number"+inputPhone);
                    if(seckill.validatePhone(inputPhone)){
                        console.log("号码填写完毕");
                        //电话写入cookie
                        $.cookie('killPhone',inputPhone,{expires:7,path:'/Seckill'});
                        console.log("写了cookie");
                        //刷新页面
                        window.location.reload();
                    }else{
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误</label>').show(1000);
                    }

                });
            }else{
                console.log("hhee");

            }
            //已经登陆   我的bug很严重啊。。。我晕了 jquery和bootstarp好像有影响 jquery不生效呢。。。
            //刷新了浏览器 cookie就没了,因为我cookie路径不对。。。
            //已经登陆，计时交互
            $.get(seckill.URL.now(),{},function(result){
                console.log(result);
                if(result){
                    var nowTime = result;
                    console.log("nowtime:"+nowTime);
                    //时间判断
                    seckill.countDown(seckillId,nowTime,startTime,endTime);
                }else{
                    console.log("result"+result);
                }

            });

        }
    }

}