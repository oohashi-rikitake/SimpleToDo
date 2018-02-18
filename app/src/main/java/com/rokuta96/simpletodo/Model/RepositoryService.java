package com.rokuta96.simpletodo.Model;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RepositoryService extends IntentService {

    public static final String LOAD_RESULT_MESSAGE = "LOAD_RESULT_MESSAGE";
    private static final String ACTION_LOAD = "com.rokuta96.simpletodo.Model.action.LOAD";
    private static final String ACTION_ADD = "com.rokuta96.simpletodo.Model.action.ADD";
    private static final String ACTION_UPDATE = "com.rokuta96.simpletodo.Model.action.UPDATE";
    private static final String ACTION_DELETE = "com.rokuta96.simpletodo.Model.action.DELETE";
    private static final String PARAM_ID = "PARAM_ID";
    private static final String PARAM_PRIORITY = "PARAM_PRIORITY";
    private static final String PARAM_TITLE = "PARAM_TITLE";
    public static Handler handlerLoad;
    public static Handler handlerLoadCompleted;

    public RepositoryService() {
        super("RepositoryService");
    }

    public static void startActionLoad(Context context) {
        Intent intent = new Intent(context, RepositoryService.class);
        intent.setAction(ACTION_LOAD);
        context.startService(intent);
    }

    public static void startActionAdd(Context context, int priority, String title) {
        Intent intent = new Intent(context, RepositoryService.class);
        intent.setAction(ACTION_ADD);
        intent.putExtra(PARAM_PRIORITY, priority);
        intent.putExtra(PARAM_TITLE, title);
        context.startService(intent);
    }

    public static void startActionUpdate(Context context, int id, int priority, String title) {
        Intent intent = new Intent(context, RepositoryService.class);
        intent.setAction(ACTION_UPDATE);
        intent.putExtra(PARAM_ID, id);
        intent.putExtra(PARAM_PRIORITY, priority);
        intent.putExtra(PARAM_TITLE, title);
        context.startService(intent);
    }

    public static void startActionDelete(Context context, int id) {
        Intent intent = new Intent(context, RepositoryService.class);
        intent.setAction(ACTION_DELETE);
        intent.putExtra(PARAM_ID, id);
        context.startService(intent);
    }

    public static void stop(Context context) {
        context.stopService(new Intent(context, RepositoryService.class));
    }

    private static SimpleToDoApi createApi() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message -> {
            Logger.d("API LOG:%s", message);
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logging).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SimpleToDoApi.API_ENDPOINT)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(SimpleToDoApi.class);
    }

    private static void handleActionLoad() {
        createApi().listToDo()
                .timeout(10, TimeUnit.SECONDS)
                .blockingSubscribe(todo_list -> {
                            Logger.i("todo_num:%d", todo_list.Items.size());
                            if (handlerLoad != null) {
                                Message msg = new Message();
                                Bundle data = new Bundle();
                                data.putSerializable(LOAD_RESULT_MESSAGE, todo_list);
                                msg.setData(data);
                                handlerLoad.sendMessage(msg);
                            }
                        }, err -> Logger.w("API LOG:%s", err.getMessage()),
                        () -> {
                            Logger.d("API LOG:complete");
                            if (handlerLoadCompleted != null) {
                                Message msg = new Message();
                                handlerLoadCompleted.sendMessage(msg);
                            }
                        }
                );
    }

    private static void handleActionAdd(int priority, String title) {
        createApi().addToDo(priority, title)
                .timeout(10, TimeUnit.SECONDS)
                .blockingSubscribe(result -> {
                            Logger.i("%s", result);
                        }, err -> Logger.w("API LOG:%s", err.getMessage()),
                        () -> Logger.d("API LOG:complete")
                );
    }

    private static void handleActionUpdate(int id, int priority, String title) {
        createApi().updateToDo(id, priority, title)
                .timeout(10, TimeUnit.SECONDS)
                .blockingSubscribe(result -> {
                            Logger.i("%s", result);
                        }, err -> Logger.w("API LOG:%s", err.getMessage()),
                        () -> Logger.d("API LOG:complete")
                );
    }

    private static void handleActionDelete(int id) {
        createApi().deleteToDo(id)
                .timeout(10, TimeUnit.SECONDS)
                .blockingSubscribe(result -> {
                            Logger.d("API LOG:%s", result);
                        }, err -> Logger.w("API LOG:%s", err.getMessage()),
                        () -> {
                            Logger.d("API LOG:complete");
                        }
                );
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent == null) {
            return;
        }

        final String action = intent.getAction();
        if (action == null) {
            return;
        }

        if (action.equals(ACTION_LOAD)) {
            handleActionLoad();
        } else if (action.equals(ACTION_ADD)) {
            final int priority = intent.getIntExtra(PARAM_PRIORITY, 0);
            final String title = intent.getStringExtra(PARAM_TITLE);
            handleActionAdd(priority, title);
        } else if (action.equals(ACTION_UPDATE)) {
            final int id = intent.getIntExtra(PARAM_ID, 0);
            final int priority = intent.getIntExtra(PARAM_PRIORITY, 0);
            final String title = intent.getStringExtra(PARAM_TITLE);
            handleActionUpdate(id, priority, title);
        } else if (action.equals(ACTION_DELETE)) {
            final int id = intent.getIntExtra(PARAM_ID, 0);
            handleActionDelete(id);
        }
    }
}
