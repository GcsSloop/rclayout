/*
 * Copyright 2018 GcsSloop
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Last modified 2018-04-16 13:11:58
 *
 * GitHub: https://github.com/GcsSloop
 * WeiBo: http://weibo.com/GcsSloop
 * WebSite: http://www.gcssloop.com
 */

package com.gcssloop.widget.helper;

public interface RCAttrs {
    void setClipBackground(boolean clipBackground);

    void setRoundAsCircle(boolean roundAsCircle);

    void setRadius(int radius);

    void setTopLeftRadius(int topLeftRadius);

    void setTopRightRadius(int topRightRadius);

    void setBottomLeftRadius(int bottomLeftRadius);

    void setBottomRightRadius(int bottomRightRadius);

    void setStrokeWidth(int strokeWidth);

    void setStrokeColor(int strokeColor);

    boolean isClipBackground();

    boolean isRoundAsCircle();

    float getTopLeftRadius();

    float getTopRightRadius();

    float getBottomLeftRadius();

    float getBottomRightRadius();

    int getStrokeWidth();

    int getStrokeColor();
}
