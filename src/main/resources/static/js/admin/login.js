//获取验证码js
function getcode() {
    var b = Math.random();
    document.getElementById("image1").src = basePath+"/images?d\x3d" + b
}
layui.config({
    base: '../../layuiadmin/' //静态资源所在路径
}).extend({
    index: 'lib/index' //主入口模块
}).use(['index', 'user'], function () {
    var $ = layui.$
        , setter = layui.setter
        , admin = layui.admin
        , form = layui.form
        , router = layui.router()
        , search = router.search;

    form.render();
    //提交
    form.on('submit(LAY-user-login-face)', function () {
        let video = document.getElementById("video2");
        let canvas = document.getElementById("canvas2");
        let ctx = canvas.getContext('2d');
        ctx.drawImage(video, 0, 0, 500, 500);
        let base64File = canvas.toDataURL();
        let formData = new FormData();
        formData.append("groupId", "101")
        formData.append("file", base64File);
        $.ajax({
            type: "post",
            url: "/faceSearch",
            data: formData,
            contentType: false,
            processData: false,
            async: false,
            success: function (text) {
                if (text.code == 0) {
                    let name = text.data.name;
                    let similar = text.data.similarValue;
                    let age = text.data.age;
                    let gender = text.data.gender;
                    window.location="/admin/login";
                    alert("姓名：" + name +"\n识别相似度：" + similar + "%" + "\n识别年龄：" + age +"\n识别性别：" + gender + "\n登录结果：成功");
                } else {
                    alert("人脸不匹配")
                }
            },
            error: function (error) {
                alert(JSON.stringify(error))
            }
        });
    });
});