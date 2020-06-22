package com.ant.recharge.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import com.ant.recharge.common.json.JacksonConverter;
import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import retrofit.client.OkClient;
import retrofit.converter.Converter;

/**
 *
 * @param <T>
 */
public class NRestAdapter<T> {

	private Context context;
	private String url;
	private Class<T> cls;

	private OkHttpClient client;

	private RestAdapter.Builder builder;

	public NRestAdapter(Context context, String url, Class<T> cls) {
		this.context = context;
		this.url = url;
		this.cls = cls;

		client = new OkHttpClient();
		initOkHttpClient();
		builder = new RestAdapter.Builder();
		builder.setConverter(new JacksonConverter()).setEndpoint(url);
	}

	public NRestAdapter(Context context, String url, Converter converter,
						Class<T> cls) {
		this.context = context;
		this.url = url;
		this.cls = cls;

		client = new OkHttpClient();
		initOkHttpClient();
		builder = new RestAdapter.Builder();
		builder.setConverter(converter).setEndpoint(url);
	}

	private void initOkHttpClient() {

		setTimeout(Profile.TIMEOUT);
		client.setRetryOnConnectionFailure(true);
		//client.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("http://192.168.1.158", 8080)));

		if (url.startsWith("https://")) {
			if (!Profile.VERIFYHOSTSSL) {
				client.setHostnameVerifier(new HostnameVerifier() {

					@Override
					public boolean verify(String hostname, SSLSession session) {
						return true;
					}
				});
			}

			if (Profile.VERIFYCERT) {
				initHttpsTrustExist();
			} else {
				initHttpsTrustAll();
			}

		}

	}

	@SuppressLint("TrulyRandom")
	private void initHttpsTrustAll() {
		try {
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				@Override
				public void checkClientTrusted(
						java.security.cert.X509Certificate[] chain,
						String authType) throws CertificateException {
				}

				@Override
				public void checkServerTrusted(
						java.security.cert.X509Certificate[] chain,
						String authType) throws CertificateException {
				}

				@Override
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			} };

			SSLContext sslContext = SSLContext.getInstance("SSL");

			sslContext.init(null, trustAllCerts,
					new java.security.SecureRandom());

			SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
			client.setSslSocketFactory(sslSocketFactory);
		} catch (NoSuchAlgorithmException e) {
			LogUtil.e(NRestAdapter.class.getSimpleName(), e.getMessage());
		} catch (KeyManagementException e) {
			LogUtil.e(NRestAdapter.class.getSimpleName(), e.getMessage());
		}

	}

	private void initHttpsTrustExist() {
		try {
			KeyStore trusted = KeyStore.getInstance("BKS");
			InputStream in = context.getAssets().open(Profile.CERTFILE);
			trusted.load(in, Profile.CERTSECRET.toCharArray());
			SSLContext sslContext = SSLContext.getInstance("TLS");
			TrustManagerFactory trustManagerFactory = TrustManagerFactory
					.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			trustManagerFactory.init(trusted);
			sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
			client.setSslSocketFactory(sslContext.getSocketFactory());
		} catch (IOException e) {
			LogUtil.e(NRestAdapter.class.getSimpleName(), e.getMessage());
		} catch (KeyStoreException e) {
			LogUtil.e(NRestAdapter.class.getSimpleName(), e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			LogUtil.e(NRestAdapter.class.getSimpleName(), e.getMessage());
		} catch (CertificateException e) {
			LogUtil.e(NRestAdapter.class.getSimpleName(), e.getMessage());
		} catch (KeyManagementException e) {
			LogUtil.e(NRestAdapter.class.getSimpleName(), e.getMessage());
		}

	}

	private boolean initNetworkStatus() {
		if (!NetworkUtil.isOpenNetwork(context)) {
            if(context != null){
//				Intent intent = new Intent();
//				intent.addFlags(intent.FLAG_ACTIVITY_NO_ANIMATION);
//				intent.setClass(context, NetworkErrActivity.class);
//				context.startActivity(intent);
            }
			/*Toast.makeText(context, context.getResources().getString(
					R.string.error_network_error), Toast.LENGTH_SHORT).show();*/
			return true;
		}
		return false;
	}

	public NRestAdapter<T> setTimeout(int seconds) {
		client.setConnectTimeout(seconds, TimeUnit.SECONDS);
		client.setReadTimeout(seconds, TimeUnit.SECONDS);
		return this;
	}

//	public NRestAdapter<T> setCookie(PersistentCookieStore cookieStore) {
//		client.interceptors().add(new RetryInterceptor(context, cookieStore));
//
//		return this;
//	}

	public OkHttpClient getOkHttpClient() {
		return client;
	}

	public RestAdapter.Builder getRestAdapterBuilder() {
		return builder;
	}

	public T create() {
		if (initNetworkStatus())
			return null;

		OkClient serviceClient = new OkClient(client);

		if (Profile.DEBUGABLE) {
			builder.setLogLevel(LogLevel.FULL);
		}
		builder.setClient(serviceClient);

		return builder.build().create(cls);
	}

	public NRestAdapter<T> setAuthenticator(final String credential) {
		builder.setRequestInterceptor(new RequestInterceptor() {
			@Override
			public void intercept(RequestFacade requestFacade) {
				requestFacade.addHeader("Authorization", credential);
			}
		});

		return this;
	}

}
