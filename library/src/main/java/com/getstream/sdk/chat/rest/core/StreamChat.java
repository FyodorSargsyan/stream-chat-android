package com.getstream.sdk.chat.rest.core;

import android.util.Log;

import com.getstream.sdk.chat.model.User;
import com.getstream.sdk.chat.rest.BaseURL;
import com.getstream.sdk.chat.rest.WebSocketService;
import com.getstream.sdk.chat.utils.Global;

import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class StreamChat {

    private final String TAG = StreamChat.class.getSimpleName();

    private String apiKey;
    private User user;
    private String userToken;
    private String clientID;

    public WebSocketService wsConnection;

    public StreamChat(String apiKey, String fcmServerKey) {
        this.apiKey = apiKey;
        Global.fcmserverkey = fcmServerKey;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUser(User user, String userToken) {
        this.userToken = userToken;
        this.user = user;
        Global.streamChat = this;
    }

    public String creatUserToken(String secretKey, String userId) {
        Map map = new HashMap<String, Object>();
        map.put("type", "JWT");
        Date date = new Date();

        long time = date.getTime() / 1000;
        return Jwts.builder().claim("user_id", userId).claim("iat", time).signWith(SignatureAlgorithm.HS256, secretKey.getBytes()).setHeader(map).compact();
    }

    public User getUser() {
        return user;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getUserToken() {
        return userToken;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public void setupWebSocket() {
        Map<String, Object> jsonParameter = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        map.put("id", this.user.getId());
        map.put("name", this.user.getName());
        map.put("image", this.user.getImage());
        jsonParameter.put("user_details", map);
        jsonParameter.put("user_id", this.user.getId());
        jsonParameter.put("user_token", this.userToken);
        jsonParameter.put("server_determines_connection_id", true);
        JSONObject json = new JSONObject(jsonParameter);
        String wsURL = Global.baseURL.url(BaseURL.Scheme.webSocket) + "connect?json=" + json + "&api_key="
                + this.apiKey + "&authorization=" + this.userToken + "&stream-auth-type=" + "jwt";
        Log.d(TAG, "WebSocket URL : " + wsURL);
        wsConnection.wsURL = wsURL;
        wsConnection.connect();
    }
}