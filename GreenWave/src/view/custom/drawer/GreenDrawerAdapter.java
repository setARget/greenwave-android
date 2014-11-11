package view.custom.drawer;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.widget.ProfilePictureView;
import com.wavon.greenwave.R;

import control.Globale;
 
/**
 * © Copyright 2014 Antoine Sauray
 * @author Antoine Sauray
 *
 */
public class GreenDrawerAdapter extends ArrayAdapter<DrawerItem> {
 
      Context context;
      List<DrawerItem> drawerItemList;
      int layoutResID;
 
      public GreenDrawerAdapter(Context context, int layoutResourceID,
                  List<DrawerItem> listItems) {
            super(context, layoutResourceID, listItems);
            this.context = context;
            this.drawerItemList = listItems;
            this.layoutResID = layoutResourceID;
 
      }
 
      @Override
      public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
 
            DrawerItemHolder drawerHolder;
            View view = convertView;
           
            if (view == null) {
                  LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                  drawerHolder = new DrawerItemHolder();
 
                  view = inflater.inflate(layoutResID, parent, false);
                  drawerHolder.itemName =(TextView) view
                              .findViewById(R.id.drawer_itemName);
                  
                  drawerHolder.fbLayout=(RelativeLayout) view
                          .findViewById(R.id.fbLayout);
                  
                  drawerHolder.itemLayout = (RelativeLayout) view
                          .findViewById(R.id.itemLayout);
                  
                  drawerHolder.fb = (com.facebook.widget.ProfilePictureView) view
                          .findViewById(R.id.drawer_fb);
          			drawerHolder.fb.setPresetSize(ProfilePictureView.SMALL);
                  
                  drawerHolder.nomFB = (TextView) view.findViewById(R.id.nomFB);

                  drawerHolder.icon= (ImageView) view.findViewById(R.id.drawer_icon);
 
                  view.setTag(drawerHolder);
 
            } else {
                  drawerHolder = (DrawerItemHolder) view.getTag();
 
            }
 
            DrawerItem dItem = (DrawerItem) this.drawerItemList.get(position);
       
            if (dItem.isFB()) {
                //drawerHolder.headerLayout.setVisibility(LinearLayout.INVISIBLE);
            	if(Globale.engine.getUtilisateur().getIdFacebook()!=null){
            		drawerHolder.fb.setProfileId(Globale.engine.getUtilisateur().getIdFacebook());
            		drawerHolder.nomFB.setText(Globale.engine.getUtilisateur().getPrenom());
            	}
            	
                drawerHolder.itemLayout.setVisibility(LinearLayout.GONE);
                drawerHolder.fb.setVisibility(LinearLayout.VISIBLE);
                drawerHolder.fbLayout.setVisibility(LinearLayout.VISIBLE);          

            }
            else {
                drawerHolder.fbLayout.setVisibility(LinearLayout.GONE);
                drawerHolder.fb.setVisibility(LinearLayout.GONE);
                drawerHolder.itemLayout.setVisibility(LinearLayout.VISIBLE);
                drawerHolder.itemName.setText(dItem.getItemName());
                drawerHolder.icon.setBackgroundResource(dItem.getImage());
          }

            return view;
      }
            
            private static class DrawerItemHolder {
                TextView itemName, title, nomFB;
                ImageView icon;
                RelativeLayout itemLayout, fbLayout;
                com.facebook.widget.ProfilePictureView fb;
          }
}
