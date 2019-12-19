package com.cocos.module_asset.ui.coin_type_select;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.cocos.library_base.base.ItemViewModel;
import com.cocos.library_base.binding.command.BindingAction;
import com.cocos.library_base.binding.command.BindingCommand;
import com.cocos.library_base.entity.AssetsModel;
import com.cocos.library_base.global.IntentKeyGlobal;
import com.cocos.library_base.global.SPKeyGlobal;
import com.cocos.library_base.router.RouterActivityPath;
import com.cocos.library_base.utils.CurrencyUtils;
import com.cocos.library_base.utils.SPUtils;
import com.cocos.library_base.utils.Utils;
import com.cocos.module_asset.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * @author ningkang.guo
 * @Date 2019/2/12
 */
public class CoinSelectItemViewModel extends ItemViewModel<CoinSelectViewModel> {

    public ObservableField<AssetsModel.AssetModel> entity = new ObservableField<>();
    public Drawable drawableImg;
    public ObservableField<String> totalValue = new ObservableField<>("≈ ￥0.00");
    public ObservableField<String> symbolType = new ObservableField<>("");
    public ObservableField<String> amount = new ObservableField<>("0.00");
    public AssetsModel.AssetModel assetModel;
    public ObservableInt amountVisible = new ObservableInt(View.INVISIBLE);

    CoinSelectItemViewModel(@NonNull CoinSelectViewModel viewModel, AssetsModel.AssetModel entity) {
        super(viewModel);
        String netType = SPUtils.getString(Utils.getContext(), SPKeyGlobal.NET_TYPE, "");
        symbolType.set(TextUtils.equals(netType, "0") ? Utils.getString(R.string.module_asset_coin_type_test) : "");
        this.entity.set(entity);
        this.assetModel = entity;
        drawableImg = ContextCompat.getDrawable(viewModel.getApplication(), R.drawable.fragment_asset_bcx_icon);
        if (assetModel.operateType == 1) {
            amountVisible.set(View.VISIBLE);
            totalValue.set(TextUtils.equals(entity.symbol, "COCOS") ? CurrencyUtils.getSingleCurrencyType() + CurrencyUtils.getCocosPrice() : CurrencyUtils.getSingleCurrencyType() + "0.00");
            NumberFormat nf = NumberFormat.getInstance();
            nf.setMaximumFractionDigits(5);
            nf.setGroupingUsed(false);
            amount.set(nf.format(entity.amount.setScale(5, RoundingMode.HALF_UP).add(BigDecimal.ZERO)));
        }
    }

    //条目的点击事件
    public BindingCommand itemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle = new Bundle();
            bundle.putSerializable(IntentKeyGlobal.ASSET_MODEL, assetModel);
            if (assetModel.operateType == 1) {
                ARouter.getInstance().build(RouterActivityPath.ACTIVITY_TRANSFER).with(bundle).navigation();
            } else if (assetModel.operateType == 2) {
                ARouter.getInstance().build(RouterActivityPath.ACTIVITY_RECEIVABLES).with(bundle).navigation();
            }
        }
    });

}
