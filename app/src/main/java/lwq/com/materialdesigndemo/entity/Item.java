package lwq.com.materialdesigndemo.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/3/10.
 */

public class Item implements Parcelable {
    private String text;
    private String color;

    public Item(String text, String color) {
        this.text = text;
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    /**
     * 内容接口描述，默认返回0即可。
     *
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.text);
        dest.writeString(this.color);
    }

    /**
     * 系统自动添加，给createFromParcel里面用
     *
     * @param in
     */
    private Item(Parcel in) {
        this.text = in.readString();
        this.color = in.readString();
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}