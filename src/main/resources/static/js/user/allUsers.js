layui.config({
    base: "/js/"
}).use(['form', 'layer', 'jquery', 'laypage'], function () {
    var form = layui.form(),
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        laypage = layui.laypage,
        $ = layui.jquery;

    $(".toPage").click(function () {
        $("#pageForm").submit();
    })

    $(".previousPage").click(function () {
        if (parseInt($("#pageNumber").val()) == 1) {
            $("#pageNumber").val(1);
            return;
        } else {
            $("#pageNumber").val(parseInt($("#pageNumber").val()) - 1);
            $("#pageForm").submit();
        }
    })

    $(".nextPage").click(function () {
        if (parseInt($("#pageNumber").val()) == parseInt($("#size").val())) {
            $("#pageNumber").val(parseInt($("#size").val()));
            return;
        } else {
            $("#pageNumber").val(parseInt($("#pageNumber").val()) + 1);
            $("#pageForm").submit();
        }
    })

    $(".toPageOne").click(function () {
        $("#pageNumber").val(1);
        $("#pageForm").submit();
    })

    $(".toPageLast").click(function () {
        $("#pageNumber").val(parseInt($("#size").val()));
        $("#pageForm").submit();
    })

    $("#pageSize").change(function () {
        $("#pageForm").submit();
    })


    //添加会员
    $(".usersAdd_btn").click(function () {
        var index = layui.layer.open({
            title: "添加用户",
            type: 2,
            content: "/manager/userAdd",
            success: function (layero, index) {
                layui.layer.tips('点击此处返回用户列表', '.layui-layer-setwin .layui-layer-close', {
                    tips: 3
                });
            }
        })
        //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
        $(window).resize(function () {
            layui.layer.full(index);
        })
        layui.layer.full(index);
    })

    //充值余额
    $(".usersRecharge_btn").click(function () {
        var index = layui.layer.open({
            title: "充值余额",
            type: 2,
            content: "/manager/userRecharge",
            success: function (layero, index) {
                layui.layer.tips('点击此处返回用户列表', '.layui-layer-setwin .layui-layer-close', {
                    tips: 3
                });
            }
        })
        //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
        $(window).resize(function () {
            layui.layer.full(index);
        })
        layui.layer.full(index);
    })


})