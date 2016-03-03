package com.snowy.babycare.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.snowy.babycare.R;
import com.snowy.babycare.bean.Student;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Snowy on 16/1/20.
 */
public class LiveContentFragment extends Fragment {

    private Button btn1_live;
    private TextView tv_result_live;
    private Button btn2_live;
    private Button btn3_live;
    private Button btn4_live;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_content_live, container, false);

        tv_result_live = (TextView) view.findViewById(R.id.tv_result_live);
        btn1_live = (Button) view.findViewById(R.id.btn1_live);
        btn2_live = (Button) view.findViewById(R.id.btn2_live);
        btn3_live = (Button) view.findViewById(R.id.btn3_live);
        btn4_live = (Button) view.findViewById(R.id.btn4_live);
        tv_result_live.setMovementMethod(ScrollingMovementMethod.getInstance());

        List<String> courses = new ArrayList<String>();
        courses.add("语文");
        courses.add("数学");
        courses.add("英语");
        courses.add("物理");
        courses.add("化学");

        Map<String, Float> scores = new HashMap<String, Float>();
        scores.put("语文", 92.5f);
        scores.put("数学", 98f);
        scores.put("英语", 83f);
        scores.put("物理", 77.5f);
        scores.put("化学", 96f);
        Date birthday = new Date(1999, 8, 16);
        final Student student1 = new Student(1, "张三", 19, birthday, courses, scores);

        tv_result_live.setText(student1.toString());

        final List<Student> students = new ArrayList<>();
        for(int i=0; i<5; i++){
            Map<String, Float> scores1 = new HashMap<String, Float>();
            scores1.put("语文", 92.5f);
            scores1.put("数学", 98f);
            scores1.put("英语", 83f);
            scores1.put("物理", 77.5f);
            scores1.put("化学", 96f);
            Student student = new Student(i, "Student" + i, 19,
                    birthday, courses, scores1);
            students.add(student);
        }

        // 过滤哪些属性需要转换
        final SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Student.class, "id", "name", "age", "courses", "scores");

        btn1_live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                tv_result_live.setText(JSON.toJSONString(students, true));
            }
        });

        final String result = JSON.toJSONString(students);
        btn2_live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_result_live.setText("");
                List<Student> stu = JSON.parseObject(result, new TypeReference<List<Student>>(){});
                for(int i=0; i<stu.size(); i++) {
                    tv_result_live.append("\n"+stu.get(i).toString()+"\n");
                }

            }
        });

        final Gson gson = new Gson();

        btn3_live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_result_live.setText("");
                String jsonStr = gson.toJson(student1);
                tv_result_live.setText(gson.fromJson(jsonStr, Student.class).toString());
            }
        });

        btn4_live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_result_live.setText("");
                String str = gson.toJson(students);
//                tv_result_live.setText(str);
                List<Student> stus = gson.fromJson(str, new TypeToken<List<Student>>(){}.getType());
                for(int i=0; i<stus.size(); i++) {
                    tv_result_live.append("\n"+stus.get(i).toString()+"\n");
                }
            }
        });


        return view;
    }
}