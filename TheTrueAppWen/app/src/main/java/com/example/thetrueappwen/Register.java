package com.example.thetrueappwen;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Register extends AppCompatActivity
{
    private DBOpenMessageUser messageregister;
    private String password3="";

    private Button login2,register2;
    private EditText reg_password1,reg_password2,reg_username;
    private  String username_str="";
    private String password_str1="";
    private String password_str2="";
    private String usercheck="0";
    public static final int CHOOSE_PHOTO=2;
    private Button chooseFromAlbum;
    private ImageView picture,yanzheng3;
    private String userpicture="";
    private String realCode,yanzheng="";
    private EditText yanzheng2;
    //权限数组（申请定位）
    private String[] permissions = new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};

    //返回code
    private static final int OPEN_SET_REQUEST_CODE = 100;
    //调用此方法判断是否拥有权限
    private void initPermissions(){
        if (lacksPermission()){//判断是否拥有权限
            //请求权限，第二参数权限String数据，第三个参数是请求码便于在onRequestPermissionsResult 方法中根据code进行判断
            ActivityCompat.requestPermissions(this, permissions,OPEN_SET_REQUEST_CODE);
        } else {
            //拥有权限执行操作
        }
    }

    //如果返回true表示缺少权限
    public boolean lacksPermission() {
        for (String permission : permissions) {
            //判断是否缺少权限，true=缺少权限
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                return true;
            }
        }
        return false;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        //创建数据库对象

        login2=(Button)findViewById(R.id.login2);
        register2=(Button)findViewById(R.id.register2);

        reg_password1=(EditText)findViewById(R.id.reg_password1);
        reg_password2=(EditText)findViewById(R.id.reg_password2);
        reg_username=(EditText)findViewById(R.id.reg_username);
        yanzheng3=(ImageView)findViewById(R.id.yanzheng3);
        yanzheng2=(EditText)findViewById(R.id.yanzheng2);
        //验证码的生成
        //将验证码用图片的形式显示出来
        yanzheng3.setImageBitmap(Code.getInstance().createBitmap());
        realCode = Code.getInstance().getCode().toLowerCase();
        yanzheng3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yanzheng3.setImageBitmap(Code.getInstance().createBitmap());
                realCode = Code.getInstance().getCode().toLowerCase();
            }
        });
        //照片选择：
         chooseFromAlbum=(Button)findViewById(R.id.choose_from_album);
         picture=(ImageView)findViewById(R.id.picture);
         chooseFromAlbum.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(ContextCompat.checkSelfPermission(Register.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
                 {
                     ActivityCompat.requestPermissions(Register.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                 }
                 else
                 {
                     openAlbum();
                 }
             }
         });

        //返回登录界面
        login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Register.this,MainActivity.class);
                Toast.makeText(Register.this,"欢迎来到登录界面",Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        });
        //确定注册界面
        register2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageregister=new DBOpenMessageUser(Register.this,"db_wen",null,1);

                username_str=reg_username.getText().toString();
                 password_str1=reg_password1.getText().toString();
                 password_str2=reg_password2.getText().toString();
                 yanzheng=yanzheng2.getText().toString();

                if(password_str1.equals("")||password_str2.equals("")||username_str.equals(""))
                {
                    Toast.makeText(Register.this,"存在没有填写的项目,请重新输入",Toast.LENGTH_SHORT).show();
                }
                else if(yanzheng.equals(""))
                {
                    Toast.makeText(Register.this,"请输入验证码",Toast.LENGTH_SHORT).show();
                }
                else if(!yanzheng.equalsIgnoreCase(realCode))
                {
                    Toast.makeText(Register.this,"验证码输入错误",Toast.LENGTH_SHORT).show();
                }
               else if(userpicture.equals(""))
                {
                   Toast.makeText(Register.this,"请选择头像",Toast.LENGTH_SHORT).show();
                }

               else if(password_str1.equals(password_str2))
                {
                    Cursor cursor=messageregister.getAllCostData(username_str);
                    if(cursor!=null){
                        while(cursor.moveToNext()){
                            password3=cursor.getString(cursor.getColumnIndex("password"));
                        }
                    }
                    else
                    {password3="";}

                    if(!password3.equals(""))
                    {
                        Toast.makeText(Register.this,"该账户已经注册过了",Toast.LENGTH_SHORT).show();
                        reg_password1.setText("");
                        reg_password2.setText("");
                        reg_username.setText("");
                    }
                    else
                    {
                        insterData(messageregister.getReadableDatabase(),username_str,password_str1,usercheck,userpicture);
                        //进行数据库的数据增加
                        Toast.makeText(Register.this,"注册成功",Toast.LENGTH_SHORT).show();
                        Toast.makeText(Register.this,"可以登录了",Toast.LENGTH_SHORT).show();
                        Intent intent2=new Intent(Register.this,MainActivity.class);
                        startActivity(intent2);
                        finish();
                    }

                }
                else
                {
                    Toast.makeText(Register.this,"输入的两次密码不一致，请重新输入",Toast.LENGTH_LONG).show();
                    reg_password1.setText("");
                    reg_password2.setText("");
                }
            }
        });
    }
    private void insterData(SQLiteDatabase sqLiteDatabase, String username, String password,String usercheck,String userpicture)
    {
        ContentValues values=new ContentValues();
        values.put("username",username);
        values.put("password",password);
        values.put("usercheck",usercheck);
        values.put("userpicture",userpicture);
        sqLiteDatabase.insert("db_wen",null,values);
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if(messageregister!=null)
            messageregister.close();
    }

//照片的选择：
    private void openAlbum()
    {
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);//这里进行的是打开相册的相关操作
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){//响应Code
            case OPEN_SET_REQUEST_CODE:
                if (grantResults.length > 0) {
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"未拥有相应权限",Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    //拥有权限执行操作
                } else {
                    Toast.makeText(this,"未拥有相应权限",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults)
//    {
//        switch (requestCode)
//        {
//            case 1:
//                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
//                {
//                    openAlbum();
//                }
//                else
//                {
//                    Toast.makeText(this,"你否定了相册请求",Toast.LENGTH_SHORT).show();
//                }
//                break;
//                default:
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
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
        if(DocumentsContract.isDocumentUri(this,uri))
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
            userpicture=imagePath;
            displayImage(imagePath);//根据图片的路径显示图片
        }
    }
    private void handleImageBeforeKitKat(Intent data)
    {
        Uri uri=data.getData();
        String imagePath=getImagePath(uri,null);
        userpicture=imagePath;
        displayImage(imagePath);
    }
    private String getImagePath(Uri uri,String selection)
    {
        String path=null;
        //通过uri和selection来获取真实的图片路径
        Cursor cursor=getContentResolver().query(uri,null,selection,null,null);
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
            Bitmap bitmap= BitmapFactory.decodeFile(imagePath);
            picture.setImageBitmap(bitmap);
        }
        else
        {
            Toast.makeText(this,"获取图片失败",Toast.LENGTH_SHORT).show();
        }
    }

}
