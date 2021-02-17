package com.mlink.zip;

public abstract class ILinkZipCallBack implements IZipCallback {
    @Override
    public void onStart() {

    }

    @Override
    public void onProgress(int percentDone) {

    }

    @Override
    public void onFinish(boolean success) {
        unZipCallBack(success);
    }


    public abstract void unZipCallBack(boolean success);

}
