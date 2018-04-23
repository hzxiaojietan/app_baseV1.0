package com.hzxiaojietan.base.business.illegalparking.view.viewholder;

import com.hzxiaojietan.base.business.illegalparking.model.bean.MyMaintainListInfo;
import com.hzxiaojietan.base.common.baseui.BaseViewHolder;

/**
 * Created by xiaojie.tan on 2017/10/26
 * MyMaintainListVH
 */
public class MyMaintainListVH extends BaseViewHolder<MyMaintainListInfo> {
    /*@BindView(R.id.img_content)
    ImageView mIvContent;
    @BindView(R.id.tv_money)
    TextView mTvMoney;
    @BindView(R.id.tv_order_no)
    TextView mTvOrderNo;
    @BindView(R.id.tv_create_time)
    TextView mTvCreateTime;
    @BindView(R.id.view_divider)
    View mViewDivider;*/

    public MyMaintainListVH() {
    }

    @Override
    public void updateView(final MyMaintainListInfo data, final int position) {
        if(data != null){
            if(0 == position){
                // 隐藏分隔符
//                mViewDivider.setVisibility(View.GONE);
            }
//            Picasso.with(mContext).load(data.imgUrl).placeholder(R.drawable.icon_placeholder).into(mIvContent);
//            mTvMoney.setText("¥"+data.money);
//            mTvOrderNo.setText(data.orderNo);
//            mTvCreateTime.setText(data.time);
        }
    }

    @Override
    public int getLayoutResId() {
//        return R.layout.item_my_maintain_list;
        return 0;
    }
}
