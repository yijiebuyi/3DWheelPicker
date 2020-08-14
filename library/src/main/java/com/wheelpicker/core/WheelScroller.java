package com.wheelpicker.core;

/*
 * Copyright (C) 2017
 * 版权所有
 *
 * 功能描述：
 *
 * 作者：yijiebuyi
 * 创建时间：2017/11/26
 *
 * 修改人：
 * 修改描述：
 * 修改日期
 */
public interface WheelScroller {
    void abortAnimation();

    boolean computeScrollOffset();

    void extendDuration(int extend);

    void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY);

    void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY, int overX, int overY);

    void forceFinished(boolean finished);

    float getCurrVelocity();

    int getCurrX();

    int getCurrY();

    int getDuration();

    int getFinalX();

    int getFinalY();

    int getStartX();

    int getStartY();

    boolean isFinished();

    void setFinalX(int newX);

    void setFinalY(int newY);

    boolean isOverScrolled();

    void notifyHorizontalEdgeReached(int startX, int finalX, int overX);

    void notifyVerticalEdgeReached(int startY, int finalY, int overY);

    void setFriction(float friction);

    boolean springBack(int startX, int startY, int minX, int maxX, int minY, int maxY);

    void startScroll(int startX, int startY, int dx, int dy);

    void startScroll(int startX, int startY, int dx, int dy, int duration);

    int timePassed();
}