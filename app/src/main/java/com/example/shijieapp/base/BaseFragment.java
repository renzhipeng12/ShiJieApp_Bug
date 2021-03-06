package com.example.shijieapp.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import com.example.shijieapp.R;


public abstract class BaseFragment extends Fragment {

    private State currentState = State.NONE;
    private View mLoadingView;
    private View mSuccessView;
    private View mErrorView;
    private View mEmptyView;

    public enum State {
        NONE, LOADING, SUCCESS, ERROR, EMPTY
    }

    private FrameLayout mBaseContainer;


    public void retry(){
        //点击了重新加载
//        LogUtils.d(this, "on retry.....");
        onRetryClick();
    }

    /**
     * 如果子fragment需要知道网络错误以后的点击，那覆盖方法即可
     */
    protected void onRetryClick() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = loadRootView(inflater,container);
        mBaseContainer = rootView.findViewById(R.id.content_view);
        loadStatesView(inflater, container);

        initView(rootView);
        initListener();
        loadData();
        return rootView;
    }

    /**
     * 如果子类需要去设置相关的事件，就可以覆盖此方法
     */
    protected void initListener() {

    }

    protected View loadRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.base_fragment_layout, container, false);
    }

    /**
     * 加载各种状态的View
     *
     * @param inflater
     * @param container
     */
    protected void loadStatesView(LayoutInflater inflater, ViewGroup container) {
        //成功的View
        mSuccessView = loadSuccessView(inflater, container);
        mBaseContainer.addView(mSuccessView);


        //Loading 的View
        mLoadingView = loadLoadingView(inflater, container);
        mBaseContainer.addView(mLoadingView);

        //错误页面
        mErrorView = loadErrorView(inflater, container);
        mBaseContainer.addView(mErrorView);

        //加载空页面
        mEmptyView = loadEmptyView(inflater, container);
        mBaseContainer.addView(mEmptyView);

        setUpState(State.NONE);

    }

    protected View loadErrorView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_error, container, false);
    }

    protected View loadEmptyView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_empty, container, false);
    }

    /**
     * 加载loading界面
     *
     * @param inflater
     * @param container
     * @return
     */
    protected View loadLoadingView(LayoutInflater inflater, ViewGroup container) {

        View loadingView = inflater.inflate(R.layout.fragment_loading, container, false);
        return loadingView;
    }

    protected View loadSuccessView(LayoutInflater inflater, ViewGroup container) {
        int resId = getRootViewResId();
        return inflater.inflate(resId, container, false);
    }

    /**
     * 子类通过这个方法来切换状态页面即可
     * @param state
     */
    public void setUpState(State state) {

        this.currentState = state;

        mSuccessView.setVisibility(currentState == State.SUCCESS ? View.VISIBLE : View.GONE);

        mLoadingView.setVisibility(currentState == State.LOADING ? View.VISIBLE : View.GONE);

        mErrorView.setVisibility(currentState == State.ERROR ? View.VISIBLE : View.GONE);

        mEmptyView.setVisibility(currentState == State.EMPTY ? View.VISIBLE : View.GONE);
    }

    protected void initView(View rootView) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        release();
    }

    protected void release() {
        //释放资源

    }

    protected void loadData() {
        //加载数据

    }

    protected abstract int getRootViewResId();

}
