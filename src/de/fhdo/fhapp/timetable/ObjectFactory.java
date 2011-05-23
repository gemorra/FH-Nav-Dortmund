
package de.fhdo.fhapp.timetable;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the de.fhdo.fhapp.timetable package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetStaffByAbbreviation_QNAME = new QName("http://timetable.fhapp.fhdo.de/", "getStaffByAbbreviation");
    private final static QName _GetStaffList_QNAME = new QName("http://timetable.fhapp.fhdo.de/", "getStaffList");
    private final static QName _GetActivitiesByDay_QNAME = new QName("http://timetable.fhapp.fhdo.de/", "getActivitiesByDay");
    private final static QName _GetActivitiesByDayResponse_QNAME = new QName("http://timetable.fhapp.fhdo.de/", "getActivitiesByDayResponse");
    private final static QName _InitializeResponse_QNAME = new QName("http://timetable.fhapp.fhdo.de/", "initializeResponse");
    private final static QName _GetActivitiesForWeek_QNAME = new QName("http://timetable.fhapp.fhdo.de/", "getActivitiesForWeek");
    private final static QName _GetStaffByAbbreviationResponse_QNAME = new QName("http://timetable.fhapp.fhdo.de/", "getStaffByAbbreviationResponse");
    private final static QName _GetCurrentBranches_QNAME = new QName("http://timetable.fhapp.fhdo.de/", "getCurrentBranches");
    private final static QName _Initialize_QNAME = new QName("http://timetable.fhapp.fhdo.de/", "initialize");
    private final static QName _GetActivitiesForWeekResponse_QNAME = new QName("http://timetable.fhapp.fhdo.de/", "getActivitiesForWeekResponse");
    private final static QName _GetStaffListResponse_QNAME = new QName("http://timetable.fhapp.fhdo.de/", "getStaffListResponse");
    private final static QName _GetCurrentBranchesResponse_QNAME = new QName("http://timetable.fhapp.fhdo.de/", "getCurrentBranchesResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: de.fhdo.fhapp.timetable
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InitializeResponse }
     * 
     */
    public InitializeResponse createInitializeResponse() {
        return new InitializeResponse();
    }

    /**
     * Create an instance of {@link GetStaffByAbbreviation }
     * 
     */
    public GetStaffByAbbreviation createGetStaffByAbbreviation() {
        return new GetStaffByAbbreviation();
    }

    /**
     * Create an instance of {@link GetStaffList }
     * 
     */
    public GetStaffList createGetStaffList() {
        return new GetStaffList();
    }

    /**
     * Create an instance of {@link GetStaffByAbbreviationResponse }
     * 
     */
    public GetStaffByAbbreviationResponse createGetStaffByAbbreviationResponse() {
        return new GetStaffByAbbreviationResponse();
    }

    /**
     * Create an instance of {@link Initialize }
     * 
     */
    public Initialize createInitialize() {
        return new Initialize();
    }

    /**
     * Create an instance of {@link GetCurrentBranchesResponse }
     * 
     */
    public GetCurrentBranchesResponse createGetCurrentBranchesResponse() {
        return new GetCurrentBranchesResponse();
    }

    /**
     * Create an instance of {@link GetActivitiesByDayResponse }
     * 
     */
    public GetActivitiesByDayResponse createGetActivitiesByDayResponse() {
        return new GetActivitiesByDayResponse();
    }

    /**
     * Create an instance of {@link GetActivitiesForWeek }
     * 
     */
    public GetActivitiesForWeek createGetActivitiesForWeek() {
        return new GetActivitiesForWeek();
    }

    /**
     * Create an instance of {@link GetStaffListResponse }
     * 
     */
    public GetStaffListResponse createGetStaffListResponse() {
        return new GetStaffListResponse();
    }

    /**
     * Create an instance of {@link GetActivitiesByDay }
     * 
     */
    public GetActivitiesByDay createGetActivitiesByDay() {
        return new GetActivitiesByDay();
    }

    /**
     * Create an instance of {@link GetCurrentBranches }
     * 
     */
    public GetCurrentBranches createGetCurrentBranches() {
        return new GetCurrentBranches();
    }

    /**
     * Create an instance of {@link GetActivitiesForWeekResponse }
     * 
     */
    public GetActivitiesForWeekResponse createGetActivitiesForWeekResponse() {
        return new GetActivitiesForWeekResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStaffByAbbreviation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://timetable.fhapp.fhdo.de/", name = "getStaffByAbbreviation")
    public JAXBElement<GetStaffByAbbreviation> createGetStaffByAbbreviation(GetStaffByAbbreviation value) {
        return new JAXBElement<GetStaffByAbbreviation>(_GetStaffByAbbreviation_QNAME, GetStaffByAbbreviation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStaffList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://timetable.fhapp.fhdo.de/", name = "getStaffList")
    public JAXBElement<GetStaffList> createGetStaffList(GetStaffList value) {
        return new JAXBElement<GetStaffList>(_GetStaffList_QNAME, GetStaffList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetActivitiesByDay }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://timetable.fhapp.fhdo.de/", name = "getActivitiesByDay")
    public JAXBElement<GetActivitiesByDay> createGetActivitiesByDay(GetActivitiesByDay value) {
        return new JAXBElement<GetActivitiesByDay>(_GetActivitiesByDay_QNAME, GetActivitiesByDay.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetActivitiesByDayResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://timetable.fhapp.fhdo.de/", name = "getActivitiesByDayResponse")
    public JAXBElement<GetActivitiesByDayResponse> createGetActivitiesByDayResponse(GetActivitiesByDayResponse value) {
        return new JAXBElement<GetActivitiesByDayResponse>(_GetActivitiesByDayResponse_QNAME, GetActivitiesByDayResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InitializeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://timetable.fhapp.fhdo.de/", name = "initializeResponse")
    public JAXBElement<InitializeResponse> createInitializeResponse(InitializeResponse value) {
        return new JAXBElement<InitializeResponse>(_InitializeResponse_QNAME, InitializeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetActivitiesForWeek }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://timetable.fhapp.fhdo.de/", name = "getActivitiesForWeek")
    public JAXBElement<GetActivitiesForWeek> createGetActivitiesForWeek(GetActivitiesForWeek value) {
        return new JAXBElement<GetActivitiesForWeek>(_GetActivitiesForWeek_QNAME, GetActivitiesForWeek.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStaffByAbbreviationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://timetable.fhapp.fhdo.de/", name = "getStaffByAbbreviationResponse")
    public JAXBElement<GetStaffByAbbreviationResponse> createGetStaffByAbbreviationResponse(GetStaffByAbbreviationResponse value) {
        return new JAXBElement<GetStaffByAbbreviationResponse>(_GetStaffByAbbreviationResponse_QNAME, GetStaffByAbbreviationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCurrentBranches }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://timetable.fhapp.fhdo.de/", name = "getCurrentBranches")
    public JAXBElement<GetCurrentBranches> createGetCurrentBranches(GetCurrentBranches value) {
        return new JAXBElement<GetCurrentBranches>(_GetCurrentBranches_QNAME, GetCurrentBranches.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Initialize }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://timetable.fhapp.fhdo.de/", name = "initialize")
    public JAXBElement<Initialize> createInitialize(Initialize value) {
        return new JAXBElement<Initialize>(_Initialize_QNAME, Initialize.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetActivitiesForWeekResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://timetable.fhapp.fhdo.de/", name = "getActivitiesForWeekResponse")
    public JAXBElement<GetActivitiesForWeekResponse> createGetActivitiesForWeekResponse(GetActivitiesForWeekResponse value) {
        return new JAXBElement<GetActivitiesForWeekResponse>(_GetActivitiesForWeekResponse_QNAME, GetActivitiesForWeekResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStaffListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://timetable.fhapp.fhdo.de/", name = "getStaffListResponse")
    public JAXBElement<GetStaffListResponse> createGetStaffListResponse(GetStaffListResponse value) {
        return new JAXBElement<GetStaffListResponse>(_GetStaffListResponse_QNAME, GetStaffListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCurrentBranchesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://timetable.fhapp.fhdo.de/", name = "getCurrentBranchesResponse")
    public JAXBElement<GetCurrentBranchesResponse> createGetCurrentBranchesResponse(GetCurrentBranchesResponse value) {
        return new JAXBElement<GetCurrentBranchesResponse>(_GetCurrentBranchesResponse_QNAME, GetCurrentBranchesResponse.class, null, value);
    }

}
