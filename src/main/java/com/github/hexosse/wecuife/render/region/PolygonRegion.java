package com.github.hexosse.wecuife.render.region;

import com.github.hexosse.wecuife.render.LineColour;
import com.github.hexosse.wecuife.render.points.PointRectangle;
import com.github.hexosse.wecuife.render.shapes.Render2DBox;
import com.github.hexosse.wecuife.render.shapes.Render2DGrid;
import com.github.hexosse.wecuife.util.Vector3;

import java.util.ArrayList;
import java.util.List;

/**
 * Main controller for a polygon-type region
 * 
 * @author yetanotherx
 * @author lahwran
 */
public class PolygonRegion extends BaseRegion
{
	
	protected List<PointRectangle> points = new ArrayList<PointRectangle>();
	protected int min;
	protected int max;
	
	private Render2DBox box;
	private Render2DGrid grid;
	
	public PolygonRegion()
	{
		super();
	}
	
	@Override
	public void render(Vector3 cameraPos)
	{
		if (this.points.size() < 1)
		{
			return;
		}
		
		for (PointRectangle point : this.points)
		{
			if (point != null)
			{
				point.render(cameraPos);
			}
		}
		
		this.box.render(cameraPos);
		this.grid.render(cameraPos);
	}
	
	@Override
	public void setMinMax(int min, int max)
	{
		this.min = min;
		this.max = max;
		this.update();
	}
	
	@Override
	public void setPolygonPoint(int id, int x, int z)
	{
		PointRectangle point = new PointRectangle(x, z);
		point.setColour(LineColour.POLYPOINT);
		point.setMinMax(this.min, this.max);
		
		if (id < this.points.size())
		{
			this.points.set(id, point);
		}
		else
		{
			for (int i = 0; i < id - this.points.size(); i++)
			{
				this.points.add(null);
			}
			this.points.add(point);
		}
		this.update();
	}
	
	private void update()
	{
		if (this.points.size() > 0)
		{
			for (PointRectangle point : this.points)
			{
				if (point != null)
				{
					point.setMinMax(this.min, this.max);
				}
			}
			
			this.box = new Render2DBox(LineColour.POLYBOX, this.points, this.min, this.max);
			this.grid = new Render2DGrid(LineColour.POLYGRID, this.points, this.min, this.max);
		}
	}
	
	@Override
	public RegionType getType()
	{
		return RegionType.POLYGON;
	}
}

