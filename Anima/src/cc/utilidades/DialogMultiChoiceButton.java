package cc.utilidades;


import java.util.Arrays;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import cc.anima.ui.R;


public class DialogMultiChoiceButton extends Button implements DialogMultiChoice {

	private AlertDialog dialog;
	private Campo campos[];
	private OnMultiChoiceChange onchange;
	
	
	public DialogMultiChoiceButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.show();
			}
		});
		
			
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DialogMultiChoice);
		
		CharSequence nombres[] = a.getTextArray(R.styleable.DialogMultiChoice_android_entries);
		CharSequence valores[] = a.getTextArray(R.styleable.DialogMultiChoice_android_entryValues);
		
		if (nombres == null)
			nombres = new CharSequence[0];
		
		if (valores == null) {
			valores = new CharSequence[nombres.length];
			Arrays.fill(valores, "0");
		} 
		
		campos = new Campo[nombres.length];
		
		for (int i = 0; i < nombres.length; i++) {
			campos[i] = new Campo();
			campos[i].nombre = nombres[i].toString();
			campos[i].valor = Integer.parseInt(valores[i].toString());		
		}
		
		Arrays.sort(campos);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);	
		builder.setAdapter(adapter, null);
		builder.setInverseBackgroundForced(true);
		builder.setTitle(a.getString(R.styleable.DialogMultiChoice_android_title));
		
		
		
		builder.setNeutralButton(R.string.ninguno, new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	                for (int i = 0; i < campos.length; i++)
	                	if (campos[i].estado) {
	                		campos[i].estado = false;
	                		if (onchange != null)
	                			onchange.onMultiChoiceChange(DialogMultiChoiceButton.this, i, false, campos[i].valor);
	                	}
	                	   
	           }});
		
		builder.setPositiveButton(android.R.string.ok, null);
		
		a.recycle();
		dialog = builder.create();		

	}


	
	private BaseAdapter adapter = new BaseAdapter() {
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			CheckBox cb = (CheckBox) convertView;

			if (cb == null) {
				cb = new CheckBox(getContext());
				cb.setTextColor(Color.BLACK);
				cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {					
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						int posicion = (Integer) buttonView.getTag();
						if (campos[posicion].estado == isChecked) return;
						campos[posicion].estado = isChecked;
						if (onchange != null) onchange.onMultiChoiceChange(DialogMultiChoiceButton.this, posicion, campos[posicion].estado, campos[posicion].valor);
					}
				});
			}
			
			cb.setTag(position);
			cb.setText(campos[position].nombre);
			cb.setChecked(campos[position].estado);
			return cb;
		}
		
		@Override
		public long getItemId(int position) {
			return 0;
		}
		
		@Override
		public Object getItem(int position) {
			return campos[position].nombre;
		}
		
		@Override
		public int getCount() {
			return campos.length;
		}
	};
	
	private static class Campo implements Comparable<Campo>{
		int valor;
		String nombre;
		boolean estado;
		
		@Override
		public int compareTo(Campo a) {
			return nombre.compareTo(a.nombre);
		}
		
	}

	@Override
	public void setOnMultiChoceChange(OnMultiChoiceChange onchange) {
		this.onchange = onchange;
	}

	
}
