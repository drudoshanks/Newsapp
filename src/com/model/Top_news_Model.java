package com.model;

import android.os.Parcel;
import android.os.Parcelable;


public class Top_news_Model implements Parcelable {

	public Top_news_Model() {

	}

	private String passing_url = "";
	private String title = "";
	private String title_sub = ""; // for sub title added on 11 may 2015
	private String image_url = "";
	private int item_type = 0; // 0 for header and 1 for item
	private int count = 0;
	// private boolean isReadHead = false;

	private String title_left = "";
	private String title_sub_left = ""; // for sub title added on 11 may 2015
	private String image_url_left = "";
	private String passing_url_left = "";
	private int leftCount = 0;
	// private boolean isReadLeft = false;

	private String title_right = "";
	private String title_sub_right = ""; // for sub title added on 11 may 2015
	private String image_url_right = "";
	private String passing_url_right = "";
	private int rightCount = 0;
	
	

	// private boolean isReadRight = false;

	// public boolean isReadHead() {
	// return isReadHead;
	// }
	//
	// public void setReadHead(boolean isReadHead) {
	// this.isReadHead = isReadHead;
	// }
	//
	// public boolean isReadLeft() {
	// return isReadLeft;
	// }
	//
	// public void setReadLeft(boolean isReadLeft) {
	// this.isReadLeft = isReadLeft;
	// }
	//
	// public boolean isReadRight() {
	// return isReadRight;
	// }
	//
	// public void setReadRight(boolean isReadRight) {
	// this.isReadRight = isReadRight;
	// }

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getLeftCount() {
		return leftCount;
	}

	public void setLeftCount(int leftCount) {
		this.leftCount = leftCount;
	}

	public int getRightCount() {
		return rightCount;
	}

	public void setRightCount(int rightCount) {
		this.rightCount = rightCount;
	}

	public String getPassing_url_left() {
		return passing_url_left;
	}

	public void setPassing_url_left(String passing_url_left) {
		this.passing_url_left = passing_url_left;
	}

	public String getPassing_url_right() {
		return passing_url_right;
	}

	public void setPassing_url_right(String passing_url_right) {
		this.passing_url_right = passing_url_right;
	}

	public String getPassing_url() {
		return passing_url;
	}

	public void setPassing_url(String passing_url) {
		this.passing_url = passing_url;
	}

	public int getItem_type() {
		return item_type;
	}

	public void setItem_type(int item_type) {
		this.item_type = item_type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public String getTitle_left() {
		return title_left;
	}

	public void setTitle_left(String title_left) {
		this.title_left = title_left;
	}

	public String getImage_url_left() {
		return image_url_left;
	}

	public void setImage_url_left(String image_url_left) {
		this.image_url_left = image_url_left;
	}

	public String getTitle_right() {
		return title_right;
	}

	public void setTitle_right(String title_right) {
		this.title_right = title_right;
	}

	public String getImage_url_right() {
		return image_url_right;
	}

	public void setImage_url_right(String image_url_right) {
		this.image_url_right = image_url_right;
	}

	public String getTitle_sub() {
		return title_sub;
	}

	public void setTitle_sub(String title_sub) {
		this.title_sub = title_sub;
	}

	public String getTitle_sub_left() {
		return title_sub_left;
	}

	public void setTitle_sub_left(String title_sub_left) {
		this.title_sub_left = title_sub_left;
	}

	public String getTitle_sub_right() {
		return title_sub_right;
	}

	public void setTitle_sub_right(String title_sub_right) {
		this.title_sub_right = title_sub_right;
	}



    protected Top_news_Model(Parcel in) {
        passing_url = in.readString();
        title = in.readString();
        title_sub = in.readString();
        image_url = in.readString();
        item_type = in.readInt();
        count = in.readInt();
        title_left = in.readString();
        title_sub_left = in.readString();
        image_url_left = in.readString();
        passing_url_left = in.readString();
        leftCount = in.readInt();
        title_right = in.readString();
        title_sub_right = in.readString();
        image_url_right = in.readString();
        passing_url_right = in.readString();
        rightCount = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(passing_url);
        dest.writeString(title);
        dest.writeString(title_sub);
        dest.writeString(image_url);
        dest.writeInt(item_type);
        dest.writeInt(count);
        dest.writeString(title_left);
        dest.writeString(title_sub_left);
        dest.writeString(image_url_left);
        dest.writeString(passing_url_left);
        dest.writeInt(leftCount);
        dest.writeString(title_right);
        dest.writeString(title_sub_right);
        dest.writeString(image_url_right);
        dest.writeString(passing_url_right);
        dest.writeInt(rightCount);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Top_news_Model> CREATOR = new Parcelable.Creator<Top_news_Model>() {
        @Override
        public Top_news_Model createFromParcel(Parcel in) {
            return new Top_news_Model(in);
        }

        @Override
        public Top_news_Model[] newArray(int size) {
            return new Top_news_Model[size];
        }
    };
}