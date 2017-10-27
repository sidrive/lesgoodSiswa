package com.lesgood.app.data.remote;


import com.lesgood.app.data.model.Transaction;
import com.lesgood.app.data.model.TransactionResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Agus on 5/13/17.
 */

public interface PaymentService {

    @POST("merchant/inquiry")
    Call<TransactionResponse> transaction(@Body Transaction transaction);
}
