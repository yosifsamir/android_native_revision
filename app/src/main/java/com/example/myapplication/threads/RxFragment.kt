package com.example.myapplication.threads

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentRxBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleEmitter
import io.reactivex.rxjava3.core.SingleOnSubscribe
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.internal.operators.single.SingleCreate
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.schedulers.Timed
import java.util.concurrent.TimeUnit

class RxFragment : BaseFragment<FragmentRxBinding>() {
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    private var singleEmitter = SingleOnSubscribe { emitter ->
        Log.d("DDDDD", "SingleOnSubscribe " + Thread.currentThread().name)
        Thread.sleep(3000)
        emitter.onSuccess("Youssef")
    }
    private var mySingleObservable:Single<String> = Single
        .create(singleEmitter)
        .subscribeOn(Schedulers.io())

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRxBinding = FragmentRxBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.observableBtn.setOnClickListener {
            compositeDisposable.add(Observable.create<String> {
                it.onNext("Youssef")
            }
                .subscribeOn(Schedulers.io())
                .map {
                    Log.d("DDDDD", Thread.currentThread().name)
                    it.uppercase()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.plus(" is my name")
                    binding.observableResultTxt.text = it.plus(" is my name")

                }, {
                    Log.d("DDDDD", it.message.toString())
                }, {
                    //onComplete
                    Log.d("DDDDD", "It is completed")
                }))
        }

        binding.singleBtn.setOnClickListener {
            compositeDisposable.add(Single
                .fromCallable {
                    Log.d("DDDDD", Thread.currentThread().name)
                    "Youssef"
                }
                .subscribeOn(Schedulers.io())
                .concatMap {
                    Log.d("DDDDD", Thread.currentThread().name)
                    Thread.sleep(2000)
                    Single.fromCallable {
                        it.uppercase()
                    }
                }
                .zipWith(
                    Single.fromCallable {
                        Log.d("DDDDD", Thread.currentThread().name)
                        Log.d("DDDDD", "zipWith is called")
                        " is my name"
                    }
                ) { s1, s2 ->
                    s1.plus(s2)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("DDDDD", Thread.currentThread().name)
                    binding.singleResultTxt.text = it
                }, {
                    Log.d("DDDDD", it.message.toString())
                }))
        }
        binding.testSingleEmitBtn.setOnClickListener {
            binding.singleResultTxt.text = ""
            //
            compositeDisposable.add(mySingleObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ // SingleOnSubscribe will be called for each subscriber .
                    Log.d("DDDDD", "1 "+Thread.currentThread().name)
                    binding.singleResultTxt.text = it
                }, {
                    Log.d("DDDDD", it.message.toString())
                }))

            compositeDisposable.add(mySingleObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ // SingleOnSubscribe will be called for each subscriber .
                    Log.d("DDDDD", "2 "+Thread.currentThread().name)
                    binding.singleResultTxt.text = it
                }, {
                    Log.d("DDDDD", it.message.toString())
                }))

            compositeDisposable.add(mySingleObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ // SingleOnSubscribe will be called for each subscriber .
                    Log.d("DDDDD", "3 "+Thread.currentThread().name)
                    binding.singleResultTxt.text = it
                }, {
                    Log.d("DDDDD", it.message.toString())
                }))
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
        compositeDisposable.dispose()

    }

}