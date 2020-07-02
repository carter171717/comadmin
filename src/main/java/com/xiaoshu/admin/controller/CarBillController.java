package com.xiaoshu.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoshu.admin.base.BaseService;
import com.xiaoshu.admin.entity.CarBill;
import com.xiaoshu.admin.entity.CreditCard;
import com.xiaoshu.admin.entity.Role;
import com.xiaoshu.admin.entity.User;
import com.xiaoshu.admin.service.*;
import com.xiaoshu.common.annotation.SysLog;
import com.xiaoshu.common.base.PageData;
import com.xiaoshu.common.util.ResponseEntity;
import com.xiaoshu.common.util.UUIDHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin/carbill")
@Slf4j
public class CarBillController extends BaseService {


    @Autowired
    UserService userService;
    @Autowired
    CarBillService carBillService;
    @Autowired
    RoleService roleService;
    @Autowired
    UploadService uploadService;


    @GetMapping("list")
    @SysLog("跳转列表页面")
    public String list(){
        return "admin/carbill/list";
    }

    @GetMapping("addOilList")
    @SysLog("跳转加油列表页面")
    public String addOilList(){
        return "admin/carbill/addOilList";
    }

    @RequiresPermissions("sys:oilbill:list")
    @PostMapping("addOilList")
    @ResponseBody
    public PageData<CarBill> addOilList(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                  @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                  ServletRequest request){
        User user = getLoginUserInfo();
        log.info("当前登录的ID为：{}",user.getLoginName());

        Map map = WebUtils.getParametersStartingWith(request, "s_");
        log.info("map的值：{}",map.toString());
        PageData<CarBill> cardPageData = new PageData<>();
        QueryWrapper<CarBill> billWrapper = new QueryWrapper<>();
        billWrapper.eq("bill_type","加油");
        if(!user.getLoginName().equals("admin")){ //admin 可以查看所有的记录
            billWrapper.eq("user_id",user.getId());
        }
        if(!map.isEmpty()){
            String type = (String) map.get("type");
            if(StringUtils.isNotBlank(type)) {
                billWrapper.eq("is_admin", "admin".equals(type) ? true : false);
            }
            String keys = (String) map.get("key");
            if(StringUtils.isNotBlank(keys)) {
                billWrapper.and(wrapper -> wrapper.like("bill_name", keys).or().like("bill_day", keys).or().like("address", keys));
            }
        }
        billWrapper.orderByDesc("bill_day");
        IPage<CarBill> cardPage = carBillService.page(new Page<>(page,limit),billWrapper);
        cardPageData.setCount(cardPage.getTotal());
        cardPageData.setData(cardPage.getRecords());
        return cardPageData;
    }


    @RequiresPermissions("sys:carbill:list")
    @PostMapping("list")
    @ResponseBody
    public PageData<CarBill> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                  @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                  ServletRequest request){
        User user = getLoginUserInfo();
        log.info("当前登录的ID为：{}",user.getLoginName());

        Map map = WebUtils.getParametersStartingWith(request, "s_");
        log.info("map的值：{}",map.toString());
        PageData<CarBill> cardPageData = new PageData<>();
        QueryWrapper<CarBill> billWrapper = new QueryWrapper<>();
        if(!user.getLoginName().equals("admin")){ //admin 可以查看所有的记录
            billWrapper.eq("user_id",user.getId());
        }
        if(!map.isEmpty()){
            String type = (String) map.get("type");
            if(StringUtils.isNotBlank(type)) {
                billWrapper.eq("is_admin", "admin".equals(type) ? true : false);
            }
            String keys = (String) map.get("key");
            if(StringUtils.isNotBlank(keys)) {
                billWrapper.and(wrapper -> wrapper.like("bill_name", keys).or().like("bill_type", keys).or().like("pay_channel", keys).or().like("address", keys));
            }
        }
        billWrapper.orderByDesc("bill_day");
        IPage<CarBill> cardPage = carBillService.page(new Page<>(page,limit),billWrapper);
        cardPageData.setCount(cardPage.getTotal());
        cardPageData.setData(cardPage.getRecords());
        return cardPageData;
    }

    @GetMapping("add")
    public String add(ModelMap modelMap){
        List<Role> roleList = roleService.selectAll();
        modelMap.put("roleList",roleList);
        return "admin/carbill/add";
    }

    @RequiresPermissions("sys:carbill:add")
    @PostMapping("add")
    @ResponseBody
    @SysLog("保存新增消费记录数据")
    public ResponseEntity add(@RequestBody  CarBill bill){
        //验证卡片必须的参数
        if(StringUtils.isBlank(bill.getBillName())){
            return ResponseEntity.failure("消费名称不能为空");
        }
        if(StringUtils.isBlank(bill.getBillNum())){
            return ResponseEntity.failure("消费金额不能为空");
        }
        if(StringUtils.isBlank(bill.getBillDay())){
            return ResponseEntity.failure("消费日期不能为空");
        }
        //shiro 获取当前登录用户的ID和姓名 补充到card信息中
        User user = getLoginUserInfo();
        log.info("当前登录的用户为：{}",user.getLoginName());
        bill.setId(UUIDHelper.getUUID32());
        bill.setUserId(user.getId());
        bill.setUserName(user.getLoginName());
        //保存消费信息
        carBillService.save(bill);
        return ResponseEntity.success("操作成功");
    }


    @GetMapping("edit")
    public String edit(String id,ModelMap modelMap){
        //User user = userService.findUserById(id);
        CarBill bill = carBillService.getById(id);
        String roleIds = "";
        List<Role> roleList = roleService.selectAll();
        modelMap.put("localBill",bill);
        modelMap.put("roleIds",roleIds);
        modelMap.put("roleList",roleList);
        return "admin/carbill/edit";
    }


    @RequiresPermissions("sys:carbill:edit")
    @PostMapping("edit")
    @ResponseBody
    @SysLog("保存信用卡编辑数据")
    public ResponseEntity edit(@RequestBody CarBill bill){
        if(StringUtils.isBlank(bill.getId())){
            return ResponseEntity.failure("ID不能为空");
        }
        if(StringUtils.isBlank(bill.getBillName())){
            return ResponseEntity.failure("消费名称不能为空");
        }
        if(StringUtils.isBlank(bill.getBillNum())){
            return ResponseEntity.failure("消费金额不能为空");
        }
        if(StringUtils.isBlank(bill.getBillDay())){
            return ResponseEntity.failure("消费日期不能为空");
        }
        carBillService.updateById(bill);
        return ResponseEntity.success("操作成功");
    }


    @RequiresPermissions("sys:carbill:delete")
    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除数据(单个)")
    public ResponseEntity delete(@RequestParam(value = "id",required = false)String id){
        if(StringUtils.isBlank(id)){
            return ResponseEntity.failure("参数错误");
        }
        CarBill bill = carBillService.getById(id);
        if(bill == null){
            return ResponseEntity.failure("用户不存在");
        }
        carBillService.removeById(id);
        return ResponseEntity.success("操作成功");
    }

    // 获取主页的数据 一次性把报表的数据全部返回
    @RequestMapping("countBillTotal")
    @ResponseBody
    public Map<String,Object> getAllBillData(){
        Map<String,Object> map = new HashMap<String,Object>();
        User user = getLoginUserInfo();
        log.info("当前登录的ID为：{}",user.getId());

        QueryWrapper<CarBill> ew = new QueryWrapper<CarBill>();
        ew.eq("user_id",user.getId());
        ew.select("IFNULL(sum(bill_num),0) as total ");
        map = carBillService.getMap(ew);
        Double amount = Double.valueOf(String.valueOf(map.get("total")));
        DecimalFormat df = new DecimalFormat("#.00");
        System.out.println(df.format(amount));
        map.put("total",df.format(amount));
        log.info("账单总金额={}",map.get("total"));
        return map;
    }

    @RequestMapping("countOilBillTotal")
    @ResponseBody
    public Map<String,Object> countOilBillTotal(){
        Map<String,Object> map = new HashMap<String,Object>();
        User user = getLoginUserInfo();
        log.info("当前登录的ID为：{}",user.getId());

        QueryWrapper<CarBill> ew = new QueryWrapper<CarBill>();
        ew.eq("user_id",user.getId());
        ew.eq("bill_type","加油");
        ew.select("IFNULL(sum(bill_num),0) as total ");
        map = carBillService.getMap(ew);
        Double amount = Double.valueOf(String.valueOf(map.get("total")));
        DecimalFormat df = new DecimalFormat("#.00");
        System.out.println(df.format(amount));
        map.put("total",df.format(amount));
        log.info("加油账单总金额={}",map.get("total"));
        return map;
    }


}
