//package com.afshin.truthordare;
//
//import org.hamcrest.Description;
//import org.hamcrest.Matcher;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//
//import static org.junit.Assert.*;
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.test.platform.app.InstrumentationRegistry;
//
//import com.afshin.truthordare.DataBase.Dao.ChallengerDao;
//import com.afshin.truthordare.MVVM.ViewModel.BuildGameViewModel;
//import com.afshin.truthordare.Repository.ChallengerRepository;
//import com.afshin.truthordare.Service.ApiService;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import javax.inject.Inject;
//
//import dagger.hilt.internal.aggregatedroot.codegen._com_afshin_truthordare_BaseApplication;
//import io.reactivex.Observable;
//import io.reactivex.Observer;
//import io.reactivex.SingleObserver;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.disposables.Disposable;
//import io.reactivex.functions.Consumer;
//import io.reactivex.observers.TestObserver;
//import io.reactivex.schedulers.Schedulers;
//import io.reactivex.subscribers.TestSubscriber;
//
//import static org.mockito.Mockito.*;
//
//import android.content.Context;
//
///**
// * Example local unit test, which will execute on the development machine (host).
// *
// * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
// */
//public class ExampleUnitTest {
//
//    @Mock
//    private ChallengerRepository challengerRepository;
//
//    @Mock
//    private BuildGameViewModel buildGameViewModel;
//
//
//
//
//    private Context context;
//
//
//    ArrayList<Challenger> challengers;
//
//
//    @Test
//    public void addition_isCorrect() {
//        assertEquals(4, 2 + 2);
//    }
//
//
//    @Test
//    public void checkChallengersSize() {
////        List<Challenger> challengers = buildWithChallengers(new Challenger(), new Challenger(), new Challenger(), new Challenger());
//        assertEquals(5, challengers.size(), 0);
//    }
//
//    @Before
//    public void setData() {
//        challengerRepository = mock(ChallengerRepository.class);
//        buildGameViewModel = new BuildGameViewModel(buildGameViewModel.getApplication(),challengerRepository );
//        buildGameViewModel.getAllChallengersLiveData().observeForever();
////        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        MockitoAnnotations.initMocks(this);
//        challengers = new ArrayList<>();
//        challengers.add(new Challenger());
//        challengers.add(new Challenger());
//        challengers.add(new Challenger());
//        challengers.add(new Challenger());
//        challengers.add(new Challenger());
//
//
//
//    }
//
////    public ArrayList<Challenger> buildWithChallengers(Challenger... challengers) {
////
////    }
//
//    @Test
//    public void startGame() {
//         buildGameViewModel.getAllChallengers();
//
//         List<Challenger> challengers = buildGameViewModel.getAllChallengersLiveData().getValue();
//         assertEquals(challengers.size(),10,5);
////        testObserver.assertValueCount(10);
//
//
////        TestObserver<Challenger> challengerObserver = Observable.fromIterable(challengers)
////                .test();
////
////        assertNotNull(this.challengers);
////        assertEquals(10, challengers.size());
//
////        challengerRepository = mock(ChallengerRepository.class);
////        TestObserver<List<Challenger>> challengers = challengerRepository.getAll().test();
//
//
////        TestObserver testObserver = challengerRepository.getAll().test();
////        assertEquals(10,testObserver.valueCount());
//
//
////                .assertSubscribed()
////                .assertValue(challengers -> {
////                    assertEquals(10,challengers.size());
////                    return false;
////                });
//
////        challengerRepository.getAll().subscribe();
//
//    }
//
//}