package com.cocos.module_asset;

import android.app.Application;

import com.cocos.library_base.config.IModuleInit;

/**
 * @author ningkang.guo
 * @Date 2019/1/28
 */
public class AssetModuleInit implements IModuleInit {
    @Override
    public boolean onInitAhead(Application application) {
        return false;
    }

    @Override
    public boolean onInitLow(Application application) {
        return false;
    }
}
