package com.news.simple_news.util

//import com.news.simple_news.model.bean.VideoBean
//import com.news.simple_news.ui.video.detail.VideoDetailActivity

class MyUtil {
    companion object {

        /**
         * 保存图片
         */
//        val filePath=Environment.getExternalStorageDirectory()
//        fun savePic( bitmap: Bitmap,context: Context){
//            //第一步，保存图片
//            val picUrl=File(filePath,"MyWall")
//            if (!picUrl.exists()){
//                picUrl.mkdirs()
//            }
//            val fileName= getToday()+".jpg"
//            val file=File(picUrl,fileName)
//            try {
//                val fos=FileOutputStream(file)
//                bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos)
//                fos.flush()
//                fos.close()
//            }catch (e:FileNotFoundException){
//                e.printStackTrace()
//            }catch (e:IOException){
//                e.printStackTrace()
//            }
//            //第二步 把文件插入到系统图库
//            try {
//                MediaStore.Images.Media.insertImage(context.contentResolver,file.absolutePath,fileName,null)
//            }catch (e:FileNotFoundException){
//                e.printStackTrace()
//            }
//            //第三步 通知图库更新
//            context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://"+file)))
//        }

    }

}