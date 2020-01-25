package com.cn.travel.web.manager;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cn.travel.cms.insurance.entity.Insurance;
import com.cn.travel.cms.insurance.service.imp.InsuranceService;
import com.cn.travel.utils.Tools;
import com.cn.travel.utils.UploadUtils;
import com.cn.travel.web.base.BaseController;
import com.cn.travel.web.base.PageParam;

@Controller
@RequestMapping("/manager")
public class InsuranceController extends BaseController{
    @Autowired
    InsuranceService insuranceService;

    @RequestMapping("/insuranceList")
    public ModelAndView insuranceList(PageParam pageParam, @RequestParam(value = "query", required = false) String query){
        ModelAndView mv = this.getModeAndView();
        if(pageParam.getPageNumber()<1){
            pageParam =new PageParam();
            long count = 0;
            try {
                count = insuranceService.count();
            } catch (Exception e) {
                e.printStackTrace();
            }
            pageParam.setCount(count);
            if(count<=10){
                pageParam.setSize(1);
            }else{
                pageParam.setSize(count%10==0?count/10:count/10+1);
            }
            pageParam.setPageNumber(1);
            pageParam.setPageSize(10);
        }
        List<Insurance> list = insuranceService.findByPage(pageParam.getPageNumber(),pageParam.getPageSize(), query);
        mv.addObject("pageData", list);
        if (Tools.notEmpty(query)) {
            mv.addObject("query", query);
            pageParam.setCount(list.size());
            if (list.size() > pageParam.getPageSize()) {
                pageParam.setSize(list.size() / pageParam.getPageSize());
            } else {
                pageParam.setSize(1);
            }
        }
        mv.addObject("pageParam",pageParam);
        mv.setViewName("insurance/insuranceList");
        return mv;
    }

    @RequestMapping("/insuranceAdd")
    public ModelAndView insuranceAdd(){
        ModelAndView mv = this.getModeAndView();
        mv.addObject("entity",new Insurance());
        mv.setViewName("insurance/insuranceEdit");
        return mv;
    }

    @RequestMapping("/insuranceView")
    public ModelAndView insuranceView(String id){
        ModelAndView mv = this.getModeAndView();
        try {
            mv.addObject("entity",insuranceService.findById(id));
        }catch (Exception e){
            e.printStackTrace();
        }
        mv.setViewName("insurance/insuranceView");
        return mv;
    }

    @RequestMapping("/insuranceEdit")
    public ModelAndView insuranceEdit(String id){
        ModelAndView mv = this.getModeAndView();
        try {
            mv.addObject("entity",insuranceService.findById(id));
        }catch (Exception e){
            e.printStackTrace();
        }
        mv.setViewName("insurance/insuranceEdit");
        return mv;
    }

    @RequestMapping("/insuranceSave")
    public String insuranceSave(HttpServletRequest request, String id, @RequestParam("fileName") MultipartFile file){
        Insurance entity = null;
        try {
            if(Tools.notEmpty(id)){
                entity = insuranceService.findById(id);
            }else{
                entity = new Insurance();
            }
            this.bindValidateRequestEntity(request,entity);
            if(file != null && !file.isEmpty()){
                String fileName = file.getOriginalFilename();
                int size = (int) file.getSize();
                System.out.println(fileName + "-->" + size);

                File fileDir = UploadUtils.getImgDirFile();
                // 输出文件夹绝对路径 – 这里的绝对路径是相当于当前项目的路径而不是“容器”路径
                System.out.println(fileDir.getAbsolutePath());
                // 构建真实的文件路径
                File newFile = new File(fileDir.getAbsolutePath()+"/insurance" + File.separator + fileName);
                // File dest = new File(path + "/" + fileName);
                if (!newFile.getParentFile().exists()) { //判断文件父目录是否存在
                    newFile.getParentFile().mkdir();
                }
                try {
                    file.transferTo(newFile); //保存文件
                    entity.setImgUrl("/insurance/" + fileName);
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (Tools.isEmpty(entity.getId())){
                entity.setId(Tools.getUUID());
                insuranceService.save(entity);
            }else{
                insuranceService.update(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return REDIRECT+"/manager/insuranceList";
    }

    @RequestMapping("/insuranceDelete")
    public String insuranceDelete(String id){
        if(Tools.notEmpty(id)){
            try {
                insuranceService.deleteByid(id);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return REDIRECT+"/manager/insuranceList";
    }
}
