package com.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.grasshopper.newsapp.R;
import com.model.GallaryImageModel;
import com.squareup.picasso.Picasso;
import com.utils.Utils;

public class GallryImage extends Fragment {

	String url = "";
	String dec = "";

	public static GallryImage newInstance(GallaryImageModel model) {
		GallryImage f = new GallryImage();
		Bundle b = new Bundle();
		b.putString("url", model.getImageUrl());
		b.putString("dec", model.getImageDescription());
		f.setArguments(b);
		return f;
	}

	View view;
	ImageView gallary_image_id;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.image_gallary, container, false);

		Bundle bundle = this.getArguments();
		url = bundle.getString("url");
		dec = bundle.getString("dec");

		gallary_image_id = (ImageView) view.findViewById(R.id.gallary_image_id);
		
		
		gallary_image_id.requestLayout();

		gallary_image_id.getLayoutParams().height = Utils
				.getImageHeight(getActivity());


		try {

			url = url.replace(" ", "%20");

			Picasso.with(getActivity()).load(Utils._url + url).fit()
					.into(gallary_image_id);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		TextView image_dec = (TextView) view.findViewById(R.id.image_dec);
		image_dec.setText(dec);

		return view;
	}

}
