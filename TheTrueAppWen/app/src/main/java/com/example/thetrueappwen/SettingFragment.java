package com.example.thetrueappwen;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;
import static com.example.thetrueappwen.Register.CHOOSE_PHOTO;

public class SettingFragment extends Fragment {
    private String username;
    private Button exitButton,registebutton,changeimage;
    private TextView user;
    private ImageView userpicture;
    private String txtpicture;
    private DBOpenMessageUser dbOpenMessageUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab4, container, false);
        super.onCreate(savedInstanceState);

        user=(TextView)view.findViewById(R.id.ming2);
        //显示图片
        userpicture=(ImageView)view.findViewById(R.id.picture3);
        user.setText(username);
        //显示头像
        dbOpenMessageUser=new DBOpenMessageUser(getActivity(),"db_wen",null,1);
        Cursor cursor=dbOpenMessageUser.getAllCostData(username);
        if(cursor!=null){
            while(cursor.moveToNext()){
                txtpicture=cursor.getString(cursor.getColumnIndex("userpicture"));
            }
        }
        Bitmap bitmap= BitmapFactory.decodeFile(txtpicture);
        userpicture.setImageBitmap(bitmap);
        //头像点击事件
        userpicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
             /*   builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });*/
                final AlertDialog dialog = builder.create();//创建对话框
                View dialogView = View.inflate(getActivity(), R.layout.showpicture, null);
                /*这个方法可以得到view,但是对view中设置居中等什么属性都是无效的，还有就是设置的宽度也是无效的，默认只能是wrap*/
                final ImageView wenpicture = (ImageView) dialogView.findViewById(R.id.wenimage1);
                Bitmap bitmap= BitmapFactory.decodeFile(txtpicture);
                wenpicture.setImageBitmap(bitmap);
                dialog.setTitle("显示头像");
                dialog.setView(dialogView);//可以通过自定义一个View，设计成自己想要的不同的dialog，(会话)
                dialog.show();//显示对话框

            }
        });

        exitButton = (Button)view.findViewById(R.id.buttonwen);
        registebutton=(Button)view.findViewById(R.id.xiugaiwen);
        changeimage=(Button)view.findViewById(R.id.butimage1);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                        .setTitle("退出登录")
                        .setMessage("是否退出当前用户登录")
                        .setPositiveButton("确定退出", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getActivity(),MainActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton("取消操作", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        }).create();
                alertDialog.show();
            }
        });
        registebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),registe.class);
                startActivity(intent);

            }
        });
        changeimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }
                else
                {
                    openAlbum();
                }
            }
        });

        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        username = ((AllWord) context).getTitles();
    }
    //更改照片
    //照片的选择：
    private void openAlbum()
    {
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);//这里进行的是打开相册的相关操作
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults)
    {
        switch (requestCode)
        {
            case 1:
                if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED)
                {
                    openAlbum();
                }
                else
                {
                    Toast.makeText(getActivity(),"你否定了相册请求",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case CHOOSE_PHOTO:
                if(resultCode==RESULT_OK)
                {
                    //判断手机系统的型号
                    if(Build.VERSION.SDK_INT>=9)
                    {
                        //4.4以及以上系统采用此方法
                        handleImageOnKitKat(data);
                    }
                    else
                    {
                        //4.4以下的系统采用此方法
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data)
    {
        String imagePath=null;
        Uri uri=data.getData();
        if(DocumentsContract.isDocumentUri(getActivity(),uri))
        {
            //如果是document类型的uri则通过document id处理
            String docId=DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority()))
            {
                String id=docId.split(":")[1];//解析为数字格式的id
                String selection= MediaStore.Images.Media._ID+"="+id;
                imagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }
            else if("com.android.providers.downloads.documents".equals(uri.getAuthority()))
            {
                Uri contentUri= ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath=getImagePath(contentUri,null);
            }
            else if("content".equalsIgnoreCase(uri.getScheme()))
            {
                //如果是content类型的uri则使用普通方式处理
                imagePath=getImagePath(uri,null);
            }
            else if("file".equalsIgnoreCase(uri.getScheme()))
            {
                //如果是file类型的uri直接获取图片路径就好
                imagePath=uri.getPath();
            }
            txtpicture=imagePath;
            displayImage(imagePath);//根据图片的路径显示图片
        }
    }
    private void handleImageBeforeKitKat(Intent data)
    {
        Uri uri=data.getData();
        String imagePath=getImagePath(uri,null);
        txtpicture=imagePath;
        displayImage(imagePath);
    }
    private String getImagePath(Uri uri,String selection)
    {
        String path=null;
        //通过uri和selection来获取真实的图片路径
        Cursor cursor=getActivity().getContentResolver().query(uri,null,selection,null,null);
        if(cursor!=null)
        {
            if(cursor.moveToFirst())
            {
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
    private void displayImage(String imagePath)
    {
        if(imagePath!=null)
        {
            dbOpenMessageUser.updatauserpicture(username,imagePath);
            Bitmap bitmap= BitmapFactory.decodeFile(imagePath);
            userpicture.setImageBitmap(bitmap);
        }
        else
        {
            Toast.makeText(getActivity(),"获取图片失败",Toast.LENGTH_SHORT).show();
        }
    }
}
