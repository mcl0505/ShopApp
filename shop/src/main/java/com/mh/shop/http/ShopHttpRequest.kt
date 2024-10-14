package com.mh.shop.http

import com.blankj.utilcode.util.GsonUtils
import com.mh55.easy.utils.LogUtil
import rxhttp.wrapper.coroutines.Await
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toAwaitApiResult
import java.io.File

object ShopHttpRequest {
    /**登录接口**/
    fun login(token: String): Await<UserInfoBean> {
        return RxHttp.postEncryptForm("v1/open")
            .add("token", token)
            .toAwaitApiResult()
    }
    /**获取用户信息**/
    fun getUserInfo(): Await<UserInfoBean> {
        return RxHttp.postEncryptForm("v1/get/user")
            .toAwaitApiResult()
    }
    /**获取任务首页信息**/
    fun getTaskIndex(): Await<TaskIndexBean> {
        return RxHttp.postEncryptForm("v1/index")
            .toAwaitApiResult()
    }
    /**获取优惠商城列表**/
    fun getShopList(): Await<MutableList<ShopBean>> {
        return RxHttp.postEncryptForm("v1/counter/list")
            .toAwaitApiResult()
    }

    fun shopExchange(type:String,counterId:String): Await<Any> {
        return RxHttp.postEncryptForm("v1/counter/exchange")
            .add("type",type)
            .add("counterId",counterId)
            .toAwaitApiResult()
    }

    /**获取我的券包列表**/
    fun getMineCouponList(offset:Int = 0): Await<MutableList<CouponPackageBean>> {
        return RxHttp.postEncryptForm("v1/counter/user/list")
            .add("offset",offset)
            .toAwaitApiResult()
    }
    /**领取券包奖励**/
    fun getCouponPackageReward(): Await<Any> {
        return RxHttp.postEncryptForm("v1/counter/receive/profit")
            .toAwaitApiResult()
    }
    /**考题列表**/
    fun getIssueList(): Await<IssueBean> {
        return RxHttp.postEncryptForm("v1/get/exam")
            .toAwaitApiResult()
    }
    /**提交考题**/
    fun submitIssue(exam_id:String,exam_data:MutableList<SubmitOptionBean>): Await<Any> {
        return RxHttp.postEncryptForm("v1/button/exam")
            .add("exam_id",exam_id)
            .add("exam_data",GsonUtils.toJson(exam_data))
            .toAwaitApiResult()
    }
    /**获取用户记录**/
    fun getUserLog(offset:Int,type:String,tab:String = ""): Await<UserLogBean> {
        return RxHttp.postEncryptForm("v1/user/logs")
            .add("offset",offset)
            .add("type",type)
            .add("tab",tab)
            .toAwaitApiResult()
    }
    /**获取兑换物品列表   1产品2挂牌商品3优惠券**/
    fun getExchangeGoodsList(offset:Int,type:String): Await<MutableList<GoodsInfoBean>> {
        return RxHttp.postEncryptForm("v1/exchange/list")
            .add("offset",offset)
            .add("type",type)
            .toAwaitApiResult()
    }
    /**兑换物品  **/
    fun exchangeGoods(exchangeId:String,num:String,name:String = "",mobile:String = "",area_text:String = ""): Await<Any> {
        return RxHttp.postEncryptForm("v1/user/exchange")
            .add("exchangeId",exchangeId)
            .add("num",num)
            .add("name",name)
            .add("mobile",mobile)
            .add("area_text",area_text)
            .toAwaitApiResult()
    }
    /**兑换物品记录  **/
    fun exchangeGoodsRecord(offset:Int,type:String = "3"): Await<MutableList<GoodsInfoRecordBean>> {
        return RxHttp.postEncryptForm("v1/exchange/user/list")
            .add("offset",offset)
            .add("type",type)
            .toAwaitApiResult()
    }
    /**提票 ticket提票**/
    fun upTicket(num:String): Await<MutableList<GoodsInfoRecordBean>> {
        return RxHttp.postEncryptForm("v1/user/extract")
            .add("num",num)
            .toAwaitApiResult()
    }

    /**推广员信息**/
    fun getPusherInfo(): Await<PusherInfoBean> {
        return RxHttp.postEncryptForm("v1/get/level/list")
            .toAwaitApiResult()
    }
    /**推广员升级**/
    fun pusherUp(): Await<Any> {
        return RxHttp.postEncryptForm("v1/save/user/level")
            .toAwaitApiResult()
    }

    /**分销员信息**/
    fun getDistributorInfo(): Await<DistributorBean> {
        return RxHttp.postEncryptForm("v1/retail/list")
            .toAwaitApiResult()
    }

    /**分销员兑换**/
    fun getDistributorExchange(id:String): Await<Any> {
        return RxHttp.postEncryptForm("v1/retail/exchange")
            .add("id",id)
            .toAwaitApiResult()
    }

    /**消耗达人列表信息**/
    fun getConsumeList(): Await<MutableList<ConsumeInfoBean>> {
        return RxHttp.postEncryptForm("v1/user/consumeValueRank")
            .toAwaitApiResult()
    }

    /**获取用户好友**/
    fun getUserFriend(offset:Int): Await<MutableList<MineFriendBean>> {
        return RxHttp.postEncryptForm("v1/user/friends")
            .add("offset",offset)
            .toAwaitApiResult()
    }

    fun getFile(
        imgPath: String, position: String = "default"
    ): Await<UploadImage> {
        val rxHttp = RxHttp.postEncryptForm("v1/upload")
        //rxHttp.setMultiForm()
        //rxHttp.addHeader("multipart/form-data")
        rxHttp.add("type", "multiple")
        rxHttp.add("position", position)
        try {
            var file = File(imgPath)
            rxHttp.addFile("file", file)
        }catch (e:Exception){
            LogUtil.d("Exception=$e")
        }


        return rxHttp.toAwaitApiResult()
    }

    /**
     * 添加反馈
     * @param type Int  类型 1.投诉 2.建议 3.不够优惠
     * @param image String 	图片 多个竖线隔开
     * @param content String  内容
     * @param c_details String 联系方式
     * @return Await<String>
     */
    fun setSuggestAdd(
        type: Int,
        images: String,
        content: String,
        mobile: String,
        linkMan: String,
    ): Await<String> {
        val rxHttp = RxHttp.postEncryptForm("v1/suggest/add")
        rxHttp.add("type", type)
            .add("image", images)
            .add("content", content)
            .add("link_phone", mobile)
            .add("link_man", linkMan)
        return rxHttp.toAwaitApiResult()
    }


    /**
     *反馈接口
     * @param r_status Int 空为所有 0.审核中 1.审核通过 2.审核失败
     * @param page Int
     * @param limit Int
     * @return IAwait<PageList<SuggestListBean>>
     */
    fun getSuggestList(
        offset: Int
    ): Await<MutableList<SuggestListBean>> {
        return RxHttp.postEncryptForm("v1/suggest/list")
            .add("offset", offset)
            .toAwaitApiResult()
    }
    fun bindAccount(
        account: String
    ): Await<Any> {
        return RxHttp.postEncryptForm("v1/user/save/account")
            .add("tradAccount", account)
            .toAwaitApiResult()
    }
}