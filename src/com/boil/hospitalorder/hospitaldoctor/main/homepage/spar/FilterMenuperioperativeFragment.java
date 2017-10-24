package com.boil.hospitalorder.hospitaldoctor.main.homepage.spar;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.base.BaseFragment;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.tagtools.FlowLayout;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.tagtools.TagAdapter;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.tagtools.TagFlowLayout;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.tagtools.TagView;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.viewtools.TypeVo;
import com.boil.hospitalorder.hospitaldoctor.userlogin.register.model.LoginDeptVo;
import com.boil.hospitalorder.hospitalmanager.main.homepage.model.CategoryItemVo;
import com.boil.hospitalorder.utils.Utils;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class FilterMenuperioperativeFragment extends BaseFragment implements OnDateSetListener{

	private View layout;
	private TextView tv_cancel;
	private TextView tv_ok;
	private TextView im_dep_type;
	private TextView im_dignose_type;
	private TextView im_date_type;
            
	
	private String[] mVals2 = new String[]{"本科病人","本人病人"};
	
	private List<TypeVo> typeVos1 = new ArrayList<TypeVo>(); 
	private List<TypeVo> typeVos2 = new ArrayList<TypeVo>(); 

    private TagFlowLayout mFlowLayout;
    private TagFlowLayout mFlowLayout2;
    private TagFlowLayout mFlowLayout3;
    private TextView tv_sure;
    
    private TextView tv_begin_date;
    private TextView tv_end_date;
    private RelativeLayout layout_end_date;
    private RelativeLayout layout_start_date;
    
	private LinearLayout LinearLayout5;
    
    private CategoryItemVo categoryItemVo = null;
    
    private SureonClick5 sureOnClickListener;
	private DatePickerDialog datePickerDialog;
	private DatePickerDialog datePickerDialog2;
	
	private int flag = 1;
	private LayoutInflater mInflater;
	private List<LoginDeptVo> vosOffices = new ArrayList<LoginDeptVo>();
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		layout = inflater.inflate(R.layout.fragment_filter_menu, container,
				false);
		initData();
		initView(layout);
		setOnListener();
		return layout;
	}

	private void initData() {
		sureOnClickListener = (PatientPerioperativeManagerActivity)getActivity();
		TypeVo vo = null;
		if(Utils.isValidate(vosOffices)) {
			for(int i = 0; i < vosOffices.size(); i++) {
				vo = new TypeVo(i, vosOffices.get(i).getHisId(), vosOffices.get(i).getName());
				typeVos1.add(vo);
			}
		}
		
		for(int i = 0; i < mVals2.length; i++) {
			vo = new TypeVo(i, i + "", mVals2[i]);
			typeVos2.add(vo);
		}
		
	}

	
	private void initView(View layout) {
		showDateDialog();
		tv_cancel = (TextView) layout.findViewById(R.id.tv_cancel);
		tv_ok = (TextView) layout.findViewById(R.id.tv_ok);
		
		im_dep_type = (TextView) layout.findViewById(R.id.im_dep_type);
		im_dignose_type = (TextView) layout.findViewById(R.id.im_dignose_type);
		im_date_type = (TextView) layout.findViewById(R.id.im_date_type);
		tv_sure = (TextView) layout.findViewById(R.id.tv_sure); 
		
		tv_begin_date = (TextView) layout.findViewById(R.id.tv_begin_date);
		tv_end_date = (TextView) layout.findViewById(R.id.tv_end_date);
		layout_end_date = (RelativeLayout) layout.findViewById(R.id.layout_end_date);
		layout_start_date = (RelativeLayout) layout.findViewById(R.id.layout_start_date);
		LinearLayout5 = (LinearLayout) layout.findViewById(R.id.LinearLayout5);
		LinearLayout5.setVisibility(View.GONE);
		tv_ok.setTypeface(iconFont2);
		tv_ok.setVisibility(View.GONE);
		tv_cancel.setTypeface(iconFont2);
		im_dep_type.setTypeface(iconFont2);
		im_dignose_type.setTypeface(iconFont2);
		im_date_type.setTypeface(iconFont2);
		mInflater = LayoutInflater.from(getActivity());
        mFlowLayout = (TagFlowLayout) layout.findViewById(R.id.id_flowlayout);
        mFlowLayout2 = (TagFlowLayout) layout.findViewById(R.id.id_flowlayout2);
        mFlowLayout3 = (TagFlowLayout) layout.findViewById(R.id.id_flowlayout3);
        mFlowLayout3.setVisibility(View.GONE);

        mFlowLayout2.setAdapter(new TagAdapter<TypeVo>(typeVos2)  
        {
            @Override
            public View getView(FlowLayout parent, int position, TypeVo s)
            {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv_blue,
                        mFlowLayout2, false);
                tv.setText(s.getTypeName());
                return tv;
            }
        });
        
        initDataView();
      
        mFlowLayout2.setOnTagClickListener(new TagFlowLayout.OnTagClickListener()
        {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent)
            {
            	categoryItemVo.setAdmTypeId(typeVos2.get(position).getTypeNo());
                return true;
            }
        });
	}
	
	public void initFlow2() {
		if(Utils.isValidate(vosOffices)) {
			TypeVo vo = null;
			typeVos1.clear();
			for(int i = 0; i < vosOffices.size(); i++) {
				vo = new TypeVo(i, vosOffices.get(i).getHisId(), vosOffices.get(i).getName());
				typeVos1.add(vo);
			}
	       mFlowLayout.setAdapter(new TagAdapter<TypeVo>(typeVos1)
	    	        {

	    	            @Override
	    	            public View getView(FlowLayout parent, int position, TypeVo s)
	    	            {
	    	                TextView tv = (TextView) mInflater.inflate(R.layout.tv,
	    	                        mFlowLayout, false);
	    	                tv.setText(s.getTypeName());
	    	                
	    	                return tv;
	    	            }
	    	        });
		}
		  mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener()
	        {
	            @Override
	            public boolean onTagClick(View view, int position, FlowLayout parent)
	            {
	            	categoryItemVo.setDepTypeId(typeVos1.get(position).getTypeNo());
	                return true;
	            }
	        });
	}
	
	public void initDataView() {
		
		tv_begin_date.setText(categoryItemVo.getStartDate());
		tv_end_date.setText(categoryItemVo.getEndDate());
		for (int i = 0; i < typeVos1.size(); i++) {
			if(typeVos1.get(i).getTypeNo().equals(categoryItemVo.getDepTypeId())) {
				mFlowLayout.doSelect((TagView)mFlowLayout.getChildAt(i), i, false);
			}
		}
		
		for (int i = 0; i < typeVos2.size(); i++) {
			if(typeVos2.get(i).getTypeNo().equals(categoryItemVo.getAdmTypeId())) {
				mFlowLayout2.doSelect((TagView)mFlowLayout2.getChildAt(i), i, false);
			}
		}
	}
	

	private void setOnListener() {
		tv_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((PatientPerioperativeManagerActivity) getActivity()).toggleFilterMenu();
				
			}
		});
		
		tv_sure.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sureOnClickListener.onClick(categoryItemVo);
			}
		});
		layout_end_date.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				flag = 2;
				datePickerDialog.show();
//				showDateDialogHour();
			}
		});
		layout_start_date.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				showDateDialogHour();
				flag = 1;
				datePickerDialog.show();				
			}
		});
		
	}

	public CategoryItemVo getCategoryItemVo() {
		return categoryItemVo;
	}

	public void setCategoryItemVo(CategoryItemVo categoryItemVo) {
		this.categoryItemVo = categoryItemVo;
	}
	
	interface SureonClick5{
		void onClick(CategoryItemVo vo);
	}

	private void showDateDialog() {
		String dateStr = "";
		if(flag == 1) {
			dateStr = categoryItemVo.getStartDate();
		} else {
			dateStr = categoryItemVo.getEndDate();
		}
		int year=Integer.valueOf(dateStr.split("-")[0]);
		int month=Integer.valueOf(dateStr.split("-")[1])-1;
		int day=Integer.valueOf(dateStr.split("-")[2]);
		
		datePickerDialog=new DatePickerDialog((Activity)getActivity(), DatePickerDialog.THEME_HOLO_LIGHT,this, year, month,day);
		DatePicker datePicker = datePickerDialog.getDatePicker();  
		datePicker.setMaxDate(new Date().getTime());
	}
	
	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		DecimalFormat df = new DecimalFormat("#00");
		String monthStr = df.format(monthOfYear+1);
		String dayStr = df.format(dayOfMonth);
		if(flag == 1) {
			categoryItemVo.setStartDate(String.format("%s-%s-%s", year,monthStr,dayStr));	
			tv_begin_date.setText(String.format("%s-%s-%s", year,monthStr,dayStr));
		} else {
			categoryItemVo.setEndDate(String.format("%s-%s-%s", year,monthStr,dayStr));	
			tv_end_date.setText(String.format("%s-%s-%s", year,monthStr,dayStr));
		}
	}

	private AlertDialog dateDialogMain;
	private DatePicker datePicker;
	private TimePicker timePicker;
	private BootstrapButton sureBtn;
	private View repair_date_other;
	
	protected void showDateDialogHour() {
		if (dateDialogMain == null) {
			dateDialogMain = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.MyDialogStyleBottom)).create();
			dateDialogMain.show();
			dateDialogMain.setCanceledOnTouchOutside(true);
			Window window = dateDialogMain.getWindow();
			window.setContentView(R.layout.activity_date_pick);
			datePicker = (DatePicker) window.findViewById(R.id.date_picker);
			timePicker = (TimePicker) window.findViewById(R.id.time_picker);
			sureBtn = (BootstrapButton) window.findViewById(R.id.repair_date_sel_ok);
			repair_date_other = window.findViewById(R.id.repair_date_other);
			datePicker.setCalendarViewShown(false);
			datePicker.setMaxDate(new Date().getTime());
			timePicker.setIs24HourView(true);
			resizePikcer(datePicker);// 调整datepicker大小
			resizePikcer(timePicker);// 调整timepicker大小
		
			
			repair_date_other.setOnClickListener(new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					dateDialogMain.dismiss();
				}
			});
			sureBtn.setOnClickListener(new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					String datestr = getData();
					if(flag == 1) {
						categoryItemVo.setStartDate(datestr);	
						tv_begin_date.setText(datestr);
					} else {
						categoryItemVo.setEndDate(datestr);	
						tv_end_date.setText(datestr);
					}
					dateDialogMain.dismiss();
				}
			});
	
		} else {
			dateDialogMain.show();
		}

	}
	
	private String getData() {
		StringBuilder str = new StringBuilder().append(datePicker.getYear()).append("-")
				.append((datePicker.getMonth() + 1) < 10 ? "0" + (datePicker.getMonth() + 1)
						: (datePicker.getMonth() + 1))
				.append("-")
				.append((datePicker.getDayOfMonth() < 10) ? "0" + datePicker.getDayOfMonth()
						: datePicker.getDayOfMonth())
				.append(" ")
				.append((timePicker.getCurrentHour() < 10) ? "0" + timePicker.getCurrentHour()
						: timePicker.getCurrentHour())
				.append(":").append((timePicker.getCurrentMinute() < 10) ? "0" + timePicker.getCurrentMinute()
						: timePicker.getCurrentMinute());

		return str.toString();
	}
	
	
	/**
	 * 调整FrameLayout大小
	 * 
	 * @param tp
	 */
	private void resizePikcer(FrameLayout tp) {
		List<NumberPicker> npList = findNumberPicker(tp);
		for (NumberPicker np : npList) {
			resizeNumberPicker(np);
		}
	}

	/*
	 * 调整numberpicker大小
	 */
	private void resizeNumberPicker(NumberPicker np) {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Utils.dip2px(getActivity(), 45),
				LayoutParams.WRAP_CONTENT);
		params.setMargins(Utils.dip2px(getActivity(), 5), 0, Utils.dip2px(getActivity(), 5), 0);
		np.setLayoutParams(params);

	}

	/**
	 * 得到viewGroup里面的numberpicker组件
	 * 
	 * @param viewGroup
	 * @return
	 */
	private List<NumberPicker> findNumberPicker(ViewGroup viewGroup) {
		List<NumberPicker> npList = new ArrayList<NumberPicker>();
		View child = null;
		if (null != viewGroup) {
			for (int i = 0; i < viewGroup.getChildCount(); i++) {
				child = viewGroup.getChildAt(i);
				if (child instanceof NumberPicker) {
					npList.add((NumberPicker) child);
				} else if (child instanceof LinearLayout) {
					List<NumberPicker> result = findNumberPicker((ViewGroup) child);
					if (result.size() > 0) {
						return result;
					}
				}
			}
		}
		return npList;
	}
	public List<LoginDeptVo> getVosOffices() {
		return vosOffices;
	}

	public void setVosOffices(List<LoginDeptVo> vosOffices) {
		this.vosOffices = vosOffices;
	}
}
