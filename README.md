# Background-Hidden-Camera
## What is this Project for?
This Code allows application to take the picture using the device camera without showing the preview of it. Any application can capture the image from front or rear camera using Foreground service. You can capture images **by running a Foreground service** using this Code.


### How it work?

Step-1: When you Open the application, this need few permission's 
      - Camera Permission 
            Application use this permission to open camera on dummy surface view to capture the picture. 
      - App Overlay Permission 
            Application use this permission to open a dummy surface view upon all apps. 
       - Storage Read and Write Permission 
            When picture captured then they save in your internal storage (name :: test.jpg), when you open app again this picture appears on activity_main.xml "ImageView" 

Step-2: On button Clik a foreground Service start, Now Close the Application When Picture captured this service stopped and a Toast message appear, after that open the application --> Work Done 

### Function

Step-3:   onPictureTaken()

- In CameraView.java a function "onPictureTaken()", After Captured done, handle this event here

In this application code "onPictureTaken" Application convert 

picture into bitmap
```
        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0,
                        data.length, opts);
        bitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, false);
```
Then resize it using this code

```     int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = 300;
        int newHeight = 300;

        // calculate the scale - in this case = 0.4f
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // createa matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
```
If you want to rotate the image

```  
        matrix.postRotate(-90);
```
resize



```  
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                width, height, matrix, true);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 40,
                bytes);
```

Step-4: Store Picture (Internal Storage)
```
File f = new File(Environment.getExternalStorageDirectory()
                        + File.separator + "test.jpg");

                System.out.println("File F : " + f);

                f.createNewFile();
                // write the bytes in file
                FileOutputStream fo = new FileOutputStream(f);
                fo.write(bytes.toByteArray());
                // remember close de FileOutput
                fo.close();
```

#### That's it.

## Demo
- You can download or import this project on Android Studio to see how it's work.

## Contribute:
#### Simple 3 step to contribute into this repo:

1. Fork the project.
2. Make required changes and commit.
3. Generate pull request. Mention all the required description regarding changes you made.

## Questions
Ping me on Linked_In [Samran Tariq](https://www.linkedin.com/in/samran-tariq/)

Best Of Luck
