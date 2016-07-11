package com.example.vinayaggarwal.registrationapp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by nitikaaggarwal on 10/07/16.
 */
public class MyConnection {
    private static MyConnection myConnection;
    private RequestQueue referenceQueue;
    private static Context mContext;

    private MyConnection(Context context)
    {
        mContext = context;
        referenceQueue = getReferenceQueue();
    }


  public RequestQueue getReferenceQueue()
  {
      if(referenceQueue == null)
      {
          referenceQueue = Volley.newRequestQueue(mContext.getApplicationContext());
      }
return referenceQueue;
  }


    public static synchronized MyConnection getInstance(Context context)
    {
        if(myConnection == null)
        {
            myConnection = new MyConnection(context);
        }
        return myConnection;
    }
    public <T>void addToRequestque(Request<T> request)
    {
        referenceQueue.add(request);
    }
}
