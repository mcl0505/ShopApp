package com.mh.shop.http

import androidx.lifecycle.MutableLiveData
import com.mh.shop.http.livedata.UserLiveData
import com.mh55.easy.ext.toast
import com.mh55.easy.mvvm.BaseViewModel
import com.mh.httplibrary.HttpConfig
import com.mh.httplibrary.ext.errorMsg
import com.mh.httplibrary.ext.rxRequestHttp
import rxhttp.asFlow

class MainViewModel : BaseViewModel() {


    fun login(token:String) {
        rxRequestHttp {
            onRequest = {
                ShopHttpRequest.login(token).asFlow().collect {
                    HttpConfig.userToken = it.token
                    getUserInfo()
                }
            }
        }
    }

    fun getUserInfo(){
        rxRequestHttp {
            onRequest = {
                ShopHttpRequest.getUserInfo().asFlow().collect {
                    UserLiveData.postValue(it)
                }
            }
        }
    }

    fun getTaskIndex(block:(info:TaskIndexBean)->Unit){
        rxRequestHttp{
            onRequest = {
                ShopHttpRequest.getTaskIndex().asFlow().collect{
                    block.invoke(it)
                }
            }
        }
    }

    val mShopList = MutableLiveData<MutableList<ShopBean>>()
    fun getShopList(){
        rxRequestHttp{
            onRequest = {
                ShopHttpRequest.getShopList().asFlow().collect{
                    mShopList.postValue(it)
                }
            }
        }
    }

    fun shopExchange(type:String,counterId:String,block: () -> Unit){
        rxRequestHttp{
            onRequest = {
                ShopHttpRequest.shopExchange(type, counterId).asFlow().collect{
                    block.invoke()
                }
            }
        }
    }
    fun getCouponPackageReward(block: () -> Unit){
        rxRequestHttp{
            onRequest = {
                ShopHttpRequest.getCouponPackageReward().asFlow().collect{
                    block.invoke()
                }
            }
        }
    }

    val mCouponPackageList = MutableLiveData<MutableList<CouponPackageBean>>()
    fun getMineCouponList(offset:Int = 0){
        rxRequestHttp{
            onRequest = {
                ShopHttpRequest.getMineCouponList(offset).asFlow().collect{
                    mCouponPackageList.postValue(it)
                }
            }
        }
    }

    val mIssueList = MutableLiveData<IssueBean>()
    fun getIssueList(){
        rxRequestHttp{
            onRequest = {
                ShopHttpRequest.getIssueList().asFlow().collect{
                    mIssueList.postValue(it)
                }
            }
        }
    }

    fun submitIssue(exam_id:String,exam_data:MutableList<SubmitOptionBean>,block: () -> Unit){
        rxRequestHttp{
            onRequest = {
                ShopHttpRequest.submitIssue(exam_id,exam_data).asFlow().collect{
                    block.invoke()
                }
            }
        }
    }

    val mUserLog = MutableLiveData<UserLogBean>()
    fun getUserLog(offset:Int,type:String,tab:String = ""){
        rxRequestHttp{
            onRequest = {
                ShopHttpRequest.getUserLog(offset, type, tab).asFlow().collect{
                    mUserLog.postValue(it)
                }
            }
        }
    }

    val mGoodsInfoList = MutableLiveData<MutableList<GoodsInfoBean>>()
    fun getExchangeGoodsList(offset:Int = 0,type:String){
        rxRequestHttp{
            onRequest = {
                ShopHttpRequest.getExchangeGoodsList(offset,type).asFlow().collect{
                    mGoodsInfoList.postValue(it)
                }
            }
        }
    }

    fun exchangeGoods(exchangeId:String,num:String,name:String = "",mobile:String = "",area_text:String = "",block: () -> Unit){
        rxRequestHttp{
            onRequest = {
                ShopHttpRequest.exchangeGoods(exchangeId, num, name, mobile, area_text).asFlow().collect{
                    block.invoke()
                }
            }
        }
    }


    val mGoodsInfoRecordList = MutableLiveData<MutableList<GoodsInfoRecordBean>>()
    fun exchangeGoodsRecord(offset:Int = 0,type:String = "3"){
        rxRequestHttp{
            onRequest = {
                ShopHttpRequest.exchangeGoodsRecord(offset,type).asFlow().collect{
                    mGoodsInfoRecordList.postValue(it)
                }
            }
        }
    }

    fun upTicket(num:String,block: () -> Unit){
        rxRequestHttp{
            onRequest = {
                ShopHttpRequest.upTicket(num).asFlow().collect{
                    block.invoke()
                }
            }
        }
    }

    val mPusherInfo = MutableLiveData<PusherInfoBean>()
    fun getPusherInfo(){
        rxRequestHttp{
            onRequest = {
                ShopHttpRequest.getPusherInfo().asFlow().collect{
                    mPusherInfo.postValue(it)
                }
            }
        }
    }

    fun pusherUp(block: () -> Unit){
        rxRequestHttp{
            onRequest = {
                ShopHttpRequest.pusherUp().asFlow().collect{
                    block.invoke()
                }
            }
        }
    }

    val mDistributorInfo = MutableLiveData<DistributorBean>()
    fun getDistributorInfo(){
        rxRequestHttp{
            onRequest = {
                ShopHttpRequest.getDistributorInfo().asFlow().collect{
                    mDistributorInfo.postValue(it)
                }
            }
        }
    }
    fun getDistributorExchange(id:String,block: () -> Unit){
        rxRequestHttp{
            onRequest = {
                ShopHttpRequest.getDistributorExchange(id).asFlow().collect{
                    block.invoke()
                }
            }
        }
    }



    val mConsumeInfo = MutableLiveData<MutableList<ConsumeInfoBean>>()
    fun getConsumeList(){
        rxRequestHttp{
            onRequest = {
                ShopHttpRequest.getConsumeList().asFlow().collect{
                    mConsumeInfo.postValue(it)
                }
            }
        }
    }

    val mUserFriendList = MutableLiveData<MutableList<MineFriendBean>>()
    fun getUserFriend(offset:Int){
        rxRequestHttp{
            onRequest = {
                ShopHttpRequest.getUserFriend(offset).asFlow().collect{
                    mUserFriendList.postValue(it)
                }
            }
        }
    }

    fun getFile(
        imgPath: String,
        position: String = "default",
        error: (() -> Unit)? = null,
        next: (String) -> Unit
    ) {
        rxRequestHttp {
            onRequest = {
                ShopHttpRequest.getFile(imgPath, position).asFlow().collect{
                    next.invoke(it.url)
                }
            }
            onError = {
                it.errorMsg.toast()
                error?.invoke() }
            isShowDialog = true
            loadingMessage = "上传中..."
        }
    }

    /**
     * 添加反馈
     * @param type Int  类型 1.投诉 2.建议 3.不够优惠
     * @param image String 	图片 多个竖线隔开
     * @param content String  内容
     * @param c_details String 联系方式
     * @param email String 邮箱
     * @return Await<String>
     */
    fun setSuggestAdd(
        type: Int = 1,
        images: String = "",
        content: String = "",
        mobile: String = "",
        linkMan: String = "",
        block: () -> Unit
    ) {
        rxRequestHttp {
            onRequest = {
                ShopHttpRequest.setSuggestAdd(
                    type,
                    images,
                    content,
                    mobile,
                    linkMan
                ).asFlow().collect {
                    block.invoke()
                }

            }
            isShowDialog = true
            loadingMessage = "提交中..."
        }
    }

    /**
     * 反馈列表--接口
     */
    val sSuggestListBeanSuccess by lazy { MutableLiveData<MutableList<SuggestListBean>>() } //反馈接口
    fun getSuggestList(offset: Int = 0) {
        rxRequestHttp {
            onRequest = {
                ShopHttpRequest.getSuggestList(offset).asFlow().collect {
                    sSuggestListBeanSuccess.postValue(it)
                }
            }
        }
    }

    fun bindAccount(account:String,block: () -> Unit){
        rxRequestHttp {
            onRequest = {
                ShopHttpRequest.bindAccount(account).asFlow().collect {
                    block.invoke()
                }
            }
        }
    }

}