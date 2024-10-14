package com.mh.shop.http

import android.os.Parcelable
import com.mh0505.shop.R
import kotlinx.parcelize.Parcelize
import java.io.Serializable

data class UserInfoBean(
    val id: String = "",   //用户uid
    val pid: String = "",//上级id
    val token: String,
    val avatar: String,
    val nickname: String,
    val mobile: String,
    val code: String,   //直推码
    val status: Int = 0,   //4=已冻结

    val score:String,//优惠券
    val contribution:String,//贡献值
    val ticket:String,//票
    val level:String,//等级
    val levelName:String,//等级名称
    val retailLevel:String,//分销
    val retailName:String,//分销名称
    val consumeValue:String,//消耗值
    val totalActive:String,//总券包值
    val tradAccount:String,//绑定的账号

    val login_time: String,//登录时间
){

    fun getUserId() = "注册ID：${id}"
}


class CancellationReasonBean (var name:String,var isSelected:Boolean=false,val type:Int=0)
@Parcelize
data class SuggestListBean(
    val content: String,
    val r_content: String, // 反馈内容
    val status: Int,
    val update_at: String, // 回复时间
    val create_at: String, // 创建时间
    var image: ArrayList<String>, // 图片
    var link_phone: String, // 联系方式
    var link_man: String // 姓名
) : Parcelable

data class TaskIndexBean(
    val active:MutableList<String> = mutableListOf(),//顶部任务列表
    val shopping:String = "",//本月已获得购物
    val shopping_monthly:String = "",//每月购物上限
    val promotion:String = "",//推广获得数量
    val advert:String = "",//已观看广告数量
    val advert_total:String = "",//需要观看的广告总数量
    val exam_status:Boolean = false,//是否答题
)

data class ShopBean(
    val id:String,
    val title:String,
    val image:String,
    val totalIncome:String,//总收入   不显示
    val incomeDays:String,//可领取次数
    val dailyProfit:String,//每次领取数量
    val desc:String,
    val buttonList:MutableList<ButtonListBean> = mutableListOf(),
    val status:Int = 1//1=可用兑换
)
data class ButtonListBean(
    val value:String,
    val title:String,
)

data class CouponPackageBean(
    val id:String,
    val title:String,
    val image:String,
    val desc:String,
)

data class IssueBean(
    val id:String,//
    val list:MutableList<TestBean>
)
data class TestBean(
    val id:String,
    val title:String,
    val option:MutableList<OptionBean>,
    val answer:MutableList<String>,
    val seq:Int,
    val type:Int,
)
data class OptionBean(
    val id:String,
    val text:String,
    val sort:String,
)
data class SubmitOptionBean(
    val id:String,
    val type:Int,
    val seq:Int,
    val answer:String
)
data class SubmitOptionBean2(
    val exam_id:String,
    val exam_data:MutableList<SubmitOptionBean>,
)

data class UserLogBean(
    val tab:MutableList<TabValue> = mutableListOf(),
    val list:MutableList<LogBean> = mutableListOf(),
    val num:String
)

data class LogBean(
    val changeValue:String,
    val state:Int,
    val desc:String,
    val createAt:String,
)

data class TabValue(
    val title:String,
    val value:String,
)

data class GoodsInfoBean(
    val id:String,//物品id
    val title:String,//名称
    val desc:String,//说明
    val amount:String,//优惠券金额
    val limit_type:String,//数量类型 0正常 9无限
    val num:String,//剩余数量
    val image:String,//图片
    val type:String,//物品类型 1产品 2挂牌商品 3优惠券
    val exchange_type:String,//兑换支付类型 1单独 2组合
    val exchange_pay:String,//支付所需方式 3优惠券4票 5贡献值
    val buy_score:String,//所需优惠券
    val buy_ticket:String,//所需票
    val buy_contribution:String,//所需贡献值
    val create_at:String,//

)

data class GoodsInfoRecordBean(
    val id:String,//
    val title:String = "",//
    val image:String = "",//
    val num:String = "",//
    val type:String = "",//
    val create_at:String = "",//
    val logistics_number:String = "",//
    val name:String = "",//
    val mobile:String = "",//
    val area_text:String = "",//
)

data class PusherInfoBean(
    val level:String = "",
    val condition:String = "",
    val upgrade_status:Int = 0,//0=不可升级   2=可升级   1=当前等级最大值
    val list:MutableList<PusherBean> = mutableListOf()
){
    fun getLevelImg() = when(level){
        "用户"-> R.mipmap.icon_mine_push_level_0
        "普通"-> R.mipmap.icon_mine_push_level_1
        "优秀"-> R.mipmap.icon_mine_push_level_2
        "精英"-> R.mipmap.icon_mine_push_level_3
        "核心"-> R.mipmap.icon_mine_push_level_4
        "顶级"-> R.mipmap.icon_mine_push_level_5
        else-> R.mipmap.icon_mine_push_level_0
    }
}

data class PusherBean(
    val id:String,
    val name:String,
    val max_num:String,
    val condition:String
)

data class ConsumeInfoBean(
    val nickname:String,
    val mobile:String,
    val consumeValue:String,
    val index:Int,
)

data class MineFriendBean(
    val mobile:String = "",
    val nickname:String = "",
    val avatar:String = "",
    val levelName:String = ""
)

data class DistributorBean(
    val retail_level:String,
    val condition:String,
    val list:MutableList<DistributorInfoBean> = mutableListOf(),
){
    fun getLevelImg() = when(retail_level){
        "用户"-> R.mipmap.icon_mine_push_level_0
        "普通"-> R.mipmap.icon_mine_push_level_1
        "优秀"-> R.mipmap.icon_mine_push_level_2
        "精英"-> R.mipmap.icon_mine_push_level_3
        "核心"-> R.mipmap.icon_mine_push_level_4
        "顶级"-> R.mipmap.icon_mine_push_level_5
        else-> R.mipmap.icon_mine_push_level_0
    }
}
data class DistributorInfoBean(
    val id:String = "",
    val name:String = "",
    val num:String = "",
    val level:String = "",
    val contribution:String = "",
    val status:Int = 0,//0=/  1=当前  3=已兑换  2=可兑换
)

data class UploadImage(
    val url:String
)