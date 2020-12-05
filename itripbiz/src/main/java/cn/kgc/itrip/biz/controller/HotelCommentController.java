package cn.kgc.itrip.biz.controller;

import cn.kgc.itrip.beans.common.ServerResponse;
import cn.kgc.itrip.beans.model.ItripComment;
import cn.kgc.itrip.beans.model.ItripImage;
import cn.kgc.itrip.beans.model.ItripUser;
import cn.kgc.itrip.beans.vo.comment.ItripAddCommentVO;
import cn.kgc.itrip.beans.vo.comment.ItripSearchCommentVO;
import cn.kgc.itrip.biz.service.IHotelCommentService;
import cn.kgc.itrip.utils.RedisApi;
import cn.kgc.itrip.utils.SystemConfig;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author 白佳庆
 * @version 1.0
 * @date 2020/10/30 15:03
 */
@Controller
@RequestMapping("/api/comment")
@Slf4j
public class HotelCommentController {
    @Resource
    IHotelCommentService iCommentService;

    @Resource
    RedisApi redisApi;

    @Resource
    private SystemConfig systemConfig;
    /**
     * 查询出游列表
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/gettraveltype",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse gettraveltype() throws Exception {
        return iCommentService.gettraveltype();
    }

    /**
     * 根据targetId查询评论照片（type=2）
     * @param targetId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getimg/{targetId}",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getimg(@PathVariable Integer targetId) throws Exception {
        return iCommentService.getimg(targetId);
    }

    /**
     * 根据酒店id查询酒店平均分
     * @param hotelId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/gethotelscore/{hotelId}",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse gethotelscore(@PathVariable Long hotelId) throws Exception {
        return iCommentService.gethotelscore(hotelId);
    }

    /**
     * 根据酒店id查询各类评论数量
     * @param hotelId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getcount/{hotelId}",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getcount(@PathVariable Integer hotelId) throws Exception {
        return iCommentService.getcount(hotelId);
    }

    /**
     * 根据评论类型查询评论列表，并分页显示
     * @param itripSearchCommentVO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getcommentlist", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getcommentlist(@RequestBody ItripSearchCommentVO itripSearchCommentVO) throws Exception {
        return iCommentService.getcommentlist(itripSearchCommentVO);
    }

    /**
     * 获取酒店相关信息(酒店名称，酒店星级)
     * @param hotelId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/gethoteldesc/{hotelId}",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse gethoteldesc(@PathVariable Integer hotelId) throws Exception {
        return iCommentService.gethoteldesc(hotelId);
    }

    /**
     * 图片删除接口
     * @param imgName
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delpic",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse delpic(@RequestParam("imgName") String imgName) throws Exception {
        return iCommentService.delpic(imgName);
    }

    /**
     * 新增评论接口
     * @param itripAddCommentVO
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse add(@RequestBody ItripAddCommentVO itripAddCommentVO, HttpServletRequest request) throws Exception {
        return iCommentService.add(itripAddCommentVO,request.getHeader("token"));
    }


//    /**
//     * 新增评论接口
//     *
//     * @param itripAddCommentVO
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    @ResponseBody
//    public ServerResponse addComment(@RequestBody ItripAddCommentVO itripAddCommentVO, HttpServletRequest request) {
//        //ItripComment
//        String tokenString = request.getHeader("token");
//        ItripUser currentUser = (ItripUser) JSONObject.parse(redisApi.get(tokenString));
//        if (null != currentUser && null != itripAddCommentVO) {
//            //新增评论，订单id不能为空
//            if (itripAddCommentVO.getOrderId() == null
//                    || itripAddCommentVO.getOrderId() == 0) {
//                return ServerResponse.createBySuccess("新增评论，订单ID不能为空", "100005");
//
//            }
//            List<ItripImage> itripImages = null;
//            ItripComment itripComment = new ItripComment();
//            itripComment.setContent(itripAddCommentVO.getContent());
//            itripComment.setHotelId(itripAddCommentVO.getHotelId());
//            itripComment.setIsHavingImg(itripAddCommentVO.getIsHavingImg());
//            itripComment.setPositionScore(itripAddCommentVO.getPositionScore());
//            itripComment.setFacilitiesScore(itripAddCommentVO.getFacilitiesScore());
//            itripComment.setHygieneScore(itripAddCommentVO.getHygieneScore());
//            itripComment.setOrderId(itripAddCommentVO.getOrderId());
//            itripComment.setServiceScore(itripAddCommentVO.getServiceScore());
//            itripComment.setProductId(itripAddCommentVO.getProductId());
//            itripComment.setProductType(itripAddCommentVO.getProductType());
//            itripComment.setIsOk(itripAddCommentVO.getIsOk());
//            itripComment.setTripMode(Long.valueOf(itripAddCommentVO.getTripMode()));
//            itripComment.setCreatedBy(currentUser.getId());
//            itripComment.setCreationDate(new Date(System.currentTimeMillis()));
//            itripComment.setUserId(currentUser.getId());
//            try {
//                if (itripAddCommentVO.getIsHavingImg() == 1) {
//                    itripImages = new ArrayList<ItripImage>();
//                    //loop input imgs array
//                    int i = 1;
//                    for (ItripImage itripImage : itripAddCommentVO.getItripImages()) {
//                        itripImage.setPosition(i);
//                        itripImage.setCreatedBy(currentUser.getId());
//                        itripImage.setCreationDate(itripComment.getCreationDate());
//                        itripImage.setType("2");
//                        itripImages.add(itripImage);
//                        i++;
//                    }
//                }
//
//                iCommentService.itriptxAddItripComment(itripComment, (null == itripImages ? new ArrayList<ItripImage>() : itripImages));
//
//                return ServerResponse .createBySuccess("新增评论成功");
//            } catch (Exception e) {
//                e.printStackTrace();
//                return ServerResponse.createByErrorMessage("新增评论失败");
//            }
//        } else if (null != currentUser && null == itripAddCommentVO) {
//            return ServerResponse.createByErrorMessage("不能提交空，请填写评论信息");
//        } else {
//            return ServerResponse.createByErrorMessage("token失效，请重登录");
//        }
//    }

    @RequestMapping("/upload")
    @ResponseBody
    public ServerResponse uploadPic(HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException {
        List<String> dataList = new ArrayList<String>();
        //创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求
        if(multipartResolver.isMultipart(request)){
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
            int fileCount = 0;
            try{
                fileCount = multiRequest.getFileMap().size();
            }catch (Exception e) {
                // TODO: handle exception
                fileCount = 0;
                return ServerResponse.createByErrorMessage("文件大小超限");
            }
            log.debug("user upload files count: " + fileCount);

            if(fileCount > 0 && fileCount <= 4 ){
                String tokenString  = multiRequest.getHeader("token");
                log.debug("token name is from header : " + tokenString);
                ItripUser itripUser = (ItripUser)JSONObject.parse(redisApi.get(tokenString));
                if(null != itripUser){
                    log.debug("user not null and id is : " + itripUser.getId());
                    //取得request中的所有文件名
                    Iterator<String> iter = multiRequest.getFileNames();
                    while(iter.hasNext()){
                        try{
                            //取得上传文件
                            MultipartFile file = multiRequest.getFile(iter.next());
                            if(file != null){
                                //取得当前上传文件的文件名称
                                String myFileName = file.getOriginalFilename();
                                //如果名称不为“”,说明该文件存在，否则说明该文件不存在
                                if(myFileName.trim() !=""
                                        &&
                                        (
                                                myFileName.toLowerCase().contains(".jpg")
                                                        || myFileName.toLowerCase().contains(".jpeg")
                                                        || myFileName.toLowerCase().contains(".png")
                                        ) ){
                                    //重命名上传后的文件名
                                    //命名规则：用户id+当前时间+随机数
                                    String suffixString = myFileName.substring(file.getOriginalFilename().indexOf("."));
                                    String fileName = itripUser.getId()+ "-" + System.currentTimeMillis() + "-" + ((int)(Math.random()*10000000)) + suffixString;
                                    //定义上传路径
                                    String path = systemConfig.getFileUploadPathString() + File.separator +fileName;
                                    log.debug("uploadFile path : " + path);
                                    File localFile = new File(path);
                                    file.transferTo(localFile);
                                    dataList.add(systemConfig.getVisitImgUrlString()+fileName);
                                }
                            }
                        }catch (Exception e) {
                            continue;
                        }
                    }
                    return ServerResponse.createBySuccess("文件上传成功",dataList);
                }else{
                    return ServerResponse.createByErrorMessage("文件上传失败");
                }
            }else{
                return ServerResponse.createByErrorMessage("上传的文件数不正确，必须是大于1小于等于4");
            }
        }else{
           return ServerResponse.createByErrorMessage("请求的内容不是上传文件的类型");
        }
    }


    @RequestMapping(value = "/delpic",produces="application/json",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse delPic(@RequestParam String imgName,HttpServletRequest request) throws IllegalStateException, IOException {
/*	public Dto<Object> delPic(
    		@RequestBody InputDto inputDto) throws IllegalStateException, IOException {*/

        String tokenString  = request.getHeader("token");
        log.debug("token name is from header : " + tokenString);
        ItripUser currentUser = (ItripUser)JSONObject.parse(redisApi.get(tokenString));
        if(null != currentUser){
            //获取物理路径
            /*		String path = systemConfig.getFileUploadPathString() + File.separator + inputDto.getParamString();*/
            String path = systemConfig.getFileUploadPathString() + File.separator + imgName;
            log.debug("delete file path : " + path);
            File file = new File(path);
            if(file.exists()){
                file.delete();
                return ServerResponse.createBySuccess("删除成功");
            }else{
                return ServerResponse.createByErrorMessage("文件不存在，删除失败");
            }
        }else{
            return ServerResponse.createByErrorMessage("token失效，请重登录");
        }
    }
}
