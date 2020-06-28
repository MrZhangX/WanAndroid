package com.example.a10850.wanandroid.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a10850.wanandroid.R;
import com.example.a10850.wanandroid.entity.ProjectBean;
import com.example.a10850.wanandroid.widget.ZQImageViewRoundOval;

import java.util.List;

import static com.example.a10850.wanandroid.widget.ZQImageViewRoundOval.TYPE_ROUND;

/***
 * 创建时间：2020/2/29 15:53
 * 创建人：10850
 * 功能描述：
 */
public class OffAccAdapter extends RecyclerView.Adapter<OffAccAdapter.OffAccHolder> {

    private Context mContext;
    private List<ProjectBean.DataBean> mList;
    private String[] stringArray;

    public OffAccAdapter(Context context, List<ProjectBean.DataBean> list) {
        this.mContext = context;
        this.mList = list;
        stringArray = mContext.getResources().getStringArray(R.array.off_acc_title);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public OffAccHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.off_acc_card_item, viewGroup, false);
        return new OffAccHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull final OffAccHolder offAccHolder, int i) {

//        helper.setText(R.id.tv_name, stringArray[helper.getAdapterPosition()])
//                .setText(R.id.tv_constellation, item.getName());

//        offAccHolder.mTv1.setText(i + "");
        offAccHolder.mTv2.setText(mList.get(i).getName());
        offAccHolder.iv.setRoundRadius(15);
        offAccHolder.iv.setType(TYPE_ROUND);

        if (mOnItemClickListener != null) {
            offAccHolder.mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = offAccHolder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(offAccHolder.mLayout, pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class OffAccHolder extends RecyclerView.ViewHolder {

        private TextView mTv1;
        private TextView mTv2;
        private LinearLayout mLayout;
        private ZQImageViewRoundOval iv;

        public OffAccHolder(@NonNull View itemView) {
            super(itemView);
            mTv1 = itemView.findViewById(R.id.tv_name);
            mTv2 = itemView.findViewById(R.id.tv_constellation);
            mLayout = itemView.findViewById(R.id.off_acc_itemc);
            iv = itemView.findViewById(R.id.iv_avatar);
        }
    }
}
