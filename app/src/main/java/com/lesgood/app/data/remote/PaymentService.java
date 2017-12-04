package com.lesgood.app.data.remote;


import com.lesgood.app.data.model.CekPayment;
import com.lesgood.app.data.model.CekPaymentResponse;
import com.lesgood.app.data.model.Transaction;
import com.lesgood.app.data.model.TransactionResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Agus on 5/13/17.
 */

public interface PaymentService {

    @POST("merchant/inquiry")
    Call<TransactionResponse> transaction(@Body Transaction transaction);

    @Headers("Accept: application/json")
    @POST("merchant/inquiry")
    Observable<TransactionResponse> sendTransactions(@Body Transaction transaction);

    @Headers("Content-Type: application/json")
    @POST("merchant/transactionStatus")
    Observable<CekPaymentResponse> cekTransactions(@Body CekPayment cekPayment);

}
