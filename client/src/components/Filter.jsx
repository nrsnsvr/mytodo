import { useSelector } from "react-redux";
import { useNavigate, useSearchParams } from 'react-router-dom'
import ToDoService from "../services/ToDoService";
import { useEffect } from "react";
import { FaSortAmountDown, FaSortAmountUp } from "react-icons/fa";

function Filter() {
   const [searchParams] = useSearchParams();
   const type = searchParams.get('type');
   const category = searchParams.get('category');
   const sortOption = searchParams.get('due');
   const { todayCount, overdueCount, upcomingCount, completedCount } = useSelector(state => state.todo);
   const { categories } = useSelector(state => state.category);
   const navigate = useNavigate();
   console.log(type, category, sortOption);
   const { getAllToDo } = ToDoService()

   const onTypeChange = (selectedType) => {
      navigate(`/?type=${selectedType}&category=${category ? category : ''}&due=${sortOption ? sortOption : 1}`)
   }

   const onCategoryChange = (selectedCategory) => {
      navigate(`/?type=${type ? type : 'today'}&category=${selectedCategory}&due=${sortOption ? sortOption : 1}`)
   }

   const onSortChange = () => {
      navigate(`/?type=${type ? type : 'today'}&category=${category ? category : ''}&due=${sortOption === "1" ? -1 : 1}`)
   }

   useEffect(() => {
      if (!type) {
         onTypeChange("today")
      } else if (!category && category !== '') {
         onCategoryChange('')
      } else if (!sortOption) {
         onSortChange()
      } else {
         getAllToDo();
      }
   }, [type, category, sortOption])

   return (
      <div className='mb-4 flex items-center justify-start gap-2 flex-wrap'>
         <input
            type="text"
            placeholder="Search todos..."
            className="text-xs md:text-sm py-1 px-2 rounded-lg border-2 border-blue-900 bg-white text-blue-950"
            data-testid="search-input"
         />
         <select
            name="status"
            className="text-xs capitalize md:text-sm py-1 px-2 rounded-lg border-2 border-blue-900 bg-blue-800 text-white hover:bg-blue-900"
            data-testid="status-filter"
         >
            <option value="all">All Status</option>
            <option value="active">Active</option>
            <option value="completed">Completed</option>
         </select>
         <select
            name="category"
            className="text-xs capitalize md:text-sm py-0 px-1 md:py-1 md:px-3  mb-2 rounded-lg border-2 border-blue-900 bg-blue-800 text-white hover:bg-blue-900"
            onChange={(e) => onCategoryChange(e.target.value)}
            data-testid="category-filter"
         >
            <option value="" selected={!category}>Category (All)</option>
            {
               categories.map(cat => {
                  return <option key={cat} value={cat} selected={category === cat}>{cat}</option>
               })
            }
         </select>
         <button
            className={`text-xs md:text-sm py-0 px-1 md:py-1 md:px-3 mb-2 rounded-lg border-2 border-blue-900 hover:border-blue-900 bg-blue-800 text-white flex items-center gap-2`}
            onClick={onSortChange}
            data-testid="type-filter"
         > Due
            {
               sortOption==1 ? <FaSortAmountUp/> : <FaSortAmountDown />
            }
         </button>
         <button
            className={`text-xs md:text-sm py-0 px-1 md:py-1 md:px-3 mb-2 rounded-lg border-2 border-blue-900 ${(type === "today" || !type) ? "bg-blue-800 text-white" : "bg-transparent text-blue-900 hover:bg-blue-100"}`}
            onClick={() => onTypeChange("today")}
            data-testid="today-tab"
         >
            Today ({todayCount || 0})
         </button>
         <button
            className={`text-xs md:text-sm py-0 px-1 md:py-1 md:px-3  mb-2 rounded-lg border-2 border-blue-900 ${type === "upcoming" ? "bg-blue-800 text-white" : "bg-transparent text-blue-900 hover:bg-blue-100"}`}
            onClick={() => onTypeChange("upcoming")}
            data-testid="upcoming-tab"
         >
            Upcoming ({upcomingCount || 0})
         </button>
         <button
            className={`text-xs md:text-sm py-0 px-1 md:py-1 md:px-3  mb-2 rounded-lg border-2 border-blue-900 ${type === "overdue" ? "bg-blue-800 text-white" : "bg-transparent text-blue-900 hover:bg-blue-100"}`}
            onClick={() => onTypeChange("overdue")}
            data-testid="overdue-tab"
         >
            Overdue ({overdueCount || 0})
         </button>
         <button
            className={`text-xs md:text-sm py-0 px-1 md:py-1 md:px-3  mb-2 rounded-lg border-2 border-blue-900 ${type === "completed" ? "bg-blue-800 text-white" : "bg-transparent text-blue-900 hover:bg-blue-100"}`}
            onClick={() => onTypeChange("completed")}
            data-testid="completed-tab"
         >
            Completed ({completedCount || 0})
         </button>

      </div>
   )
}

export default Filter;