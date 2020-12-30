package com.arpan.alosproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arpan.alosproject.R;
import com.arpan.alosproject.model.firebase.AllCourse;
import com.arpan.alosproject.ui.activities.CourseActivity;

import java.util.List;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseViewHolder> {

    private Context context;
    private List<AllCourse> allCourses;

    public CourseListAdapter(Context context, List<AllCourse> allCourses) {
        this.context = context;
        this.allCourses = allCourses;
    }

    public CourseListAdapter() {
    }

    public void updateValue(List<AllCourse> allCourses) {
        this.allCourses = allCourses;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        AllCourse course = allCourses.get(position);

        holder.courseName.setText(course.getCourseName());
        holder.courseDescription.setText(course.getCourseDescription());
        holder.courseLanguage.setText(course.getCourseLanguage());

        holder.startNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CourseActivity.class);
                intent.putExtra("COURSE", course);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allCourses.size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {

        TextView courseName;
        TextView courseDescription;
        TextView courseLanguage;
        Button startNowButton;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);

            courseName = itemView.findViewById(R.id.courseName);
            courseDescription = itemView.findViewById(R.id.courseDescription);
            courseLanguage = itemView.findViewById(R.id.courseLanguage);
            startNowButton = itemView.findViewById(R.id.courseStartNowButton);
        }
    }
}
