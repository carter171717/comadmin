layui.use(['layer','form','table'], function() {
    var layer = layui.layer,
        $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        t;                  //表格数据变量

    t = {
        elem: '#oilbillTable',
        url:'/admin/carbill/addOilList',
        method:'post',
        page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
            layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'], //自定义分页布局
            //,curr: 5 //设定初始在第 5 页
            groups: 6, //只显示 1 个连续页码
            first: "首页", //显示首页
            last: "尾页", //显示尾页
            limits:[3,10, 20, 30]
        },
        width: $(parent.window).width()-223,
        cols: [[
            {type:'checkbox'},
            {field:'billName', title: '名称', width:'15%'},
            {field:'billNum',  title: '加油金额',    width:'15%'},
            {field:'address',     title: '加油地点',    width:'15%' },
            {field:'billDay',     title: '加油日期',    width:'15%' },
            {field:'billType',    title: '消费类型',width:'10%'},
            {field:'payChannel',       title: '支付方式',    width:'10%'},
            {field:'remark', title: '备注'}
        ]]
    };
    table.render(t);

    //监听工具条
    table.on('tool(oilbillList)', function(obj){
        var data = obj.data;

    });


    $('.layui-inline .layui-btn').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    //搜索
    form.on("submit(searchForm)",function(data){
        t.where = data.field;
        table.reload('oilbillTable', t);
        return false;
    });

});